package com.xuesitu.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.MyApplication;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.app.EventBusTagUtils;
import com.lib.app.FragmentTag;
import com.lib.app.FragmentUtils;
import com.lib.bean.Event;
import com.lib.bean.PushDetailBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.animation_deal.AnimationUtil;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.fastkit.views.viewpager.my.MyViewPager;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.bugly.BuglyUtil;
import com.lib.utls.pop.PushPopupUtils;
import com.lib.view.navigationbar.xuesitu.HomeNavigationBar;
import com.live.activity.MainRoomActivity;
import com.share.fragment.ShareFragment;
import com.user.bean.BaseBean;
import com.user.fragment.PersonalFragment;
import com.xuesitu.R;
import com.xuesitu.bean.CheckTokenBean;
import com.xuesitu.bean.QiNiuBean;
import com.youxuan.fragment.LiveTestFragment;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.App_MainActivity)
public class MainActivity extends BaseAppActivity {

    @BindView(R.id.mViewPager)
    MyViewPager mViewPager;
    @BindView(R.id.iv_youxuan)
    ImageView ivYouxuan;
    @BindView(R.id.tv_youxuan)
    TextView tvYouxuan;
    @BindView(R.id.lin_youxuan)
    LinearLayout linYouxuan;
    @BindView(R.id.iv_sixue)
    ImageView ivSixue;
    @BindView(R.id.tv_sixue)
    TextView tvSixue;
    @BindView(R.id.lin_sixue)
    LinearLayout linSixue;
    @BindView(R.id.iv_jianke)
    ImageView ivJianke;
    @BindView(R.id.tv_jianke)
    TextView tvJianke;
    @BindView(R.id.lin_jianke)
    LinearLayout linJianke;
    @BindView(R.id.iv_fengxiang)
    ImageView ivFengxiang;
    @BindView(R.id.tv_fengxiang)
    TextView tvFengxiang;
    @BindView(R.id.lin_fengxiang)
    LinearLayout linFengxiang;
    @BindView(R.id.iv_center)
    ImageView ivCenter;
    @BindView(R.id.lin_personal)
    LinearLayout linPersonal;

    @BindView(R.id.home_bar)
    HomeNavigationBar homeBar;

    private boolean PersonalFragmentIsShow = false;
    private DemandAdapter mDemandAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    private PushPopupUtils pushPopupUtils;

    @Override
    protected void onCreateView() {


        pushPopupUtils = new PushPopupUtils(this);
        initData();
        initView();


        PermissionUtil.getInstance(this).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });

        requestQiniuToken();
        requestCheckToken();


        bindYoumengTokenByUser();


    }


    /**
     * 登录后需要绑定
     * 每次进入页面如何用户Token和友盟Token都不为空那么再次绑定
     */
    private void bindYoumengTokenByUser() {
        String userToken = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();
        String youMengToken = SharedPreferenceManager.getInstance(this).getUserCache().getYouMengPushToken();
        if (!userToken.equals("") && !youMengToken.equals("")) {
            bindYouMengToken();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initData() {

        fragments.clear();
        fragments.add(FragmentUtils.getYouXuanFragment());
        fragments.add(FragmentUtils.getSiXueFragment());
        fragments.add(FragmentUtils.getDayiFragment());
        fragments.add(FragmentUtils.getJianKeFragment());

        fragments.add(FragmentUtils.getShareFragemnt());

        homeBar.setViewType(HomeNavigationBar.YOU_XUAN);
        initPersonalFragment();


    }

    private PersonalFragment personalFragment;

    private void initPersonalFragment() {

        personalFragment = new PersonalFragment();
        FragmentCustomUtils.setFragment(this, R.id.f_personal, personalFragment, FragmentTag.PersonalFragment);
    }


    private void initView() {


        mViewPager.setOffscreenPageLimit(4);
        mDemandAdapter = new DemandAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mDemandAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        initSelected(0);
    }


    @OnClick({R.id.lin_youxuan, R.id.lin_sixue, R.id.lin_jianke, R.id.lin_fengxiang, R.id.iv_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_youxuan:
                mViewPager.setCurrentItem(0);

                initSelected(0);


                homeBar.setViewType(HomeNavigationBar.YOU_XUAN);
                break;
            case R.id.lin_sixue:
                mViewPager.setCurrentItem(1);
                initSelected(1);

                homeBar.setViewType(HomeNavigationBar.YOU_WEN_DA);
                break;
            case R.id.iv_center:
                mViewPager.setCurrentItem(2);
                initSelected(2);

                homeBar.setViewType(HomeNavigationBar.YOU_TU_ZI);
                break;
            case R.id.lin_jianke:
                mViewPager.setCurrentItem(3);
                initSelected(3);
                homeBar.setViewType(HomeNavigationBar.YOU_JIAN_KE);

                break;
            case R.id.lin_fengxiang:
                mViewPager.setCurrentItem(4);
                initSelected(4);

                homeBar.setViewType(HomeNavigationBar.YOU_FENG_XIANG);
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();


        menuHide();


    }

    private void initSelected(int position) {

        initBottomImage();

        switch (position) {
            case 0: {
                ivYouxuan.setImageResource(R.mipmap.icon_preferably_selected);
                tvYouxuan.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }

            case 1: {
                ivSixue.setImageResource(R.mipmap.icon_study_selected);
                tvSixue.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }

            case 2: {
                ivCenter.setImageResource(R.mipmap.look_selected1);
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        ivCenter.setImageResource(R.mipmap.look_selected2);

                    }
                }, 200);
                break;
            }

            case 3: {
                ivJianke.setImageResource(R.mipmap.icon_course_selected);
                tvJianke.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }
            case 4: {
                ivFengxiang.setImageResource(R.mipmap.icon_fenshare_selected);
                tvFengxiang.setTextColor(getResources().getColor(R.color.base_blue));
                break;
            }
        }


    }

    private void initBottomImage() {
        ivYouxuan.setImageResource(R.mipmap.icon_preferably_default);
        ivSixue.setImageResource(R.mipmap.icon_study_default);
        ivCenter.setImageResource(R.mipmap.look_default);
        ivJianke.setImageResource(R.mipmap.icon_course_default);
        ivFengxiang.setImageResource(R.mipmap.icon_fenshare_default);

        tvYouxuan.setTextColor(getResources().getColor(R.color.base_gray));
        tvSixue.setTextColor(getResources().getColor(R.color.base_gray));
        tvJianke.setTextColor(getResources().getColor(R.color.base_gray));
        tvFengxiang.setTextColor(getResources().getColor(R.color.base_gray));


    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }


    /**
     * 适配器
     */
    public class DemandAdapter extends FragmentPagerAdapter {


        public DemandAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    private int count = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


            count++;

            if (count > 1) {
                //  showFinishDialog();


                //ActivityCollector.finishAll();
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {
                showToast("再按一次退出应用");

            }

            return true;
        }
        return true;
    }


    @Subscriber(tag = EventBusTagUtils.PersonalFragment)
    public void fromPersonalFragment(Event event) {

        switch (event.getEventCode()) {

            case 1: {


                setMenu();


                break;
            }

        }


    }


    @Subscriber(tag = EventBusTagUtils.LoginActivity)
    public void fromLoginActivity(Event event) {

        switch (event.getEventCode()) {

            case 1: {


                bindYouMengToken();


                break;
            }

        }


    }


    /**
     * @param event
     */
    @Subscriber(tag = EventBusTagUtils.HomeNavigationBar)
    public void fromHomeNavigationBar(Event event) {

        switch (event.getEventCode()) {

            case 1: {


                String token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();


                if (token.equals("")) {

                    ARouter.getInstance().build(ARouterPathUtils.User_LoginActivity).navigation();

                } else {

                    setMenu();

                }

                break;
            }

            case 2: {
                //打开消息列表

                String token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();


                if (token.equals("")) {

                    ARouter.getInstance().build(ARouterPathUtils.User_LoginActivity).navigation();

                } else {

                    ARouter.getInstance().build(ARouterPathUtils.User_MessageActivity).navigation();

                }


                break;
            }

        }


    }

    private void bindYouMengToken() {
        HttpUtils.with(this)
                .addParam("requestType", "SET_APPID")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("app_id", SharedPreferenceManager.getInstance(this).getUserCache().getYouMengPushToken())
                .addParam("client", "android")

                .execute(new HttpNormalCallBack<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean result) {

                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


    /**
     * 全局登录监听
     *
     * @param event
     */
    @Subscriber(tag = EventBusTagUtils.HttpCallBack)
    public void fromHttpCallBack(Event event) {

        switch (event.getEventCode()) {

            case 1: {

                //Token失效
                SharedPreferenceManager.getInstance(this).getUserCache().setUserToken("");

                showToast("Token失效请重新登录!");


                break;
            }

        }


    }

    /**
     * 去分享圈了解更多
     *
     * @param event
     */
    @Subscriber(tag = EventBusTagUtils.MyClassDetailActivity)
    public void fromMyClassDetailActivity(Event event) {

        switch (event.getEventCode()) {

            case 1: {


                mViewPager.setCurrentItem(4);
                initSelected(4);
                homeBar.setViewType(HomeNavigationBar.YOU_FENG_XIANG);


                break;
            }

        }


    }

    private void setMenu() {
        switch (linPersonal.getVisibility()) {
            case View.VISIBLE: {
                menuHide();
                break;
            }


            case View.GONE: {
                menuShow();

            }
        }
    }

    private void menuHide() {
        if (linPersonal.getVisibility() == View.VISIBLE) {

            linPersonal.setVisibility(View.GONE);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(AnimationUtil.leftToView());
            animationSet.addAnimation(AnimationUtil.Alpha1To0());
            linPersonal.startAnimation(animationSet);

            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    linPersonal.setBackgroundResource(R.color.alpha_black100);
                }

                @Override
                public void onAnimationEnd(Animation animation) {


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }


    }

    private void menuShow() {

        if (linPersonal.getVisibility() == View.GONE) {


            linPersonal.setVisibility(View.VISIBLE);


            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(AnimationUtil.rightToView());
            animationSet.addAnimation(AnimationUtil.Alpha0To1());
            linPersonal.startAnimation(animationSet);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    linPersonal.setBackgroundResource(R.color.alpha_black60);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            personalFragment.initUserInfo();

        }
    }

    private void requestQiniuToken() {


        HttpUtils.with(this)
                .post()
                .addParam("requestType", "QUERY_QNTOKEN")
                .execute(new HttpNormalCallBack<QiNiuBean>() {
                    @Override
                    public void onSuccess(QiNiuBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            SharedPreferenceManager.getInstance(MainActivity.this).getUserCache().setQiNiuToken(result.getObj().getToken());

                            SharedPreferenceManager.getInstance(MainActivity.this).getUserCache().setQiNiuUrl(result.getObj().getBaseurl());
                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


    private void requestCheckToken() {

        String token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();


        HttpUtils.with(this)
                .post()
                .addParam("requestType", "CHENCK_TOKEN")
                .addParam("token", token)
                .execute(new HttpNormalCallBack<CheckTokenBean>() {
                    @Override
                    public void onSuccess(CheckTokenBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                        } else {

                            SharedPreferenceManager.getInstance(MainActivity.this).getUserCache().setUserToken("");

                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }


}
