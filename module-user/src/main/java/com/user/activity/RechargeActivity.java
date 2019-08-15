package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.utils.pop.WriteMoneyNumUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_RechargeActivity)
public class RechargeActivity extends BaseAppActivity {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.lin_wei)
    LinearLayout linWei;
    @BindView(R2.id.lin_zhifu)
    LinearLayout linZhifu;
    @BindView(R2.id.btn_recharge)
    Button btnRecharge;
    @BindView(R2.id.iv_wei)
    ImageView ivWei;
    @BindView(R2.id.iv_zhifu)
    ImageView ivZhifu;


    private int tubi[] = {100, 200, 500, 1000, 2000, 0};

    List<Integer> mData = new ArrayList<>();
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreateView() {
        initTitle();
        initData();

        homeAdapter = new HomeAdapter(this, mData);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(homeAdapter);
    }

    private void initData() {
        mData.clear();
        for (int i : tubi) {
            mData.add(i);
        }
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("充值")
                .setRightIcon(R.mipmap.nav_share)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击分享");
                    }
                })
                .builder();

        // navigationBar.getRightTextView()
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }


    @OnClick({R2.id.lin_wei, R2.id.lin_zhifu, R2.id.btn_recharge})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_wei) {
            ivWei.setImageResource(R.mipmap.icon_choose);

            ivZhifu.setImageResource(R.mipmap.icon_default);

        } else if (i == R.id.lin_zhifu) {
            ivWei.setImageResource(R.mipmap.icon_default);

            ivZhifu.setImageResource(R.mipmap.icon_choose);
        } else if (i == R.id.btn_recharge) {

        }
    }

    private List<View> viewList = new ArrayList<>();
    private List<TextView> textViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //手动输入的充值金额
    private int moneyWrite = 0;

    public class HomeAdapter extends BaseAdapter<Integer> {

        public HomeAdapter(Context context, List<Integer> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_recharge;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<Integer> mData) {

            final View view = holder.getView(R.id.v_bg);
            final TextView textView = holder.getView(R.id.tv_tb);

            final Button btn_song = holder.getView(R.id.btn_song);


            textViewList.add(textView);
            viewList.add(view);
            holder.getView(R.id.lin_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    initViewBg();
                    view.setBackgroundResource(R.drawable.bg_part_circle4_select);
                    textView.setTextColor(getResources().getColor(R.color.white));

                    WriteMoneyNumUtils writeMoneyNumUtils = new WriteMoneyNumUtils(RechargeActivity.this);
                    writeMoneyNumUtils.setWriteMoneyNumUtilsListener(new WriteMoneyNumUtils.WriteMoneyNumUtilsListener() {
                        @Override
                        public void onMumberSure(int num) {
                            moneyWrite = num;
                            textView.setText(num + "兔币");
                        }
                    });
                    writeMoneyNumUtils.showAnswerPopuPopu(view);
                    writeMoneyNumUtils.setEt_num(moneyWrite);

                }


            });

            if (position == mData.size() - 1) {


                btn_song.setVisibility(View.INVISIBLE);
                textView.setText("手动输入");
            } else {
                btn_song.setVisibility(View.VISIBLE);
                btn_song.setText("送100兔币");
            }

        }
    }

    private void initViewBg() {


        for (View view : viewList) {

            view.setBackgroundResource(R.drawable.bg_part_circle4_normal);

        }


        for (TextView textView : textViewList) {
            textView.setTextColor(getResources().getColor(R.color.base_title));

        }
    }

}
