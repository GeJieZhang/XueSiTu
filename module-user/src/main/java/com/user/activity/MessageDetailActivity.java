package com.user.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.user.R;
import com.user.R2;
import com.user.bean.MessageDetail;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = ARouterPathUtils.User_MessageDetailActivity)
public class MessageDetailActivity extends BaseAppActivity {


    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.tv_time)
    TextView tvTime;
    @Autowired(name = "id")
    String id;

    @Override
    protected void onCreateView() {

        ARouter.getInstance().inject(this);
        initTitle();

        initData();
    }

    private void initData() {

        HttpUtils.with(this)
                .addParam("requestType", "GET_NEWS_BY_ID")
                .addParam("token", SharedPreferenceManager.getInstance(this).getUserCache().getUserToken())
                .addParam("id", id)
                .execute(new HttpNormalCallBack<MessageDetail>() {
                    @Override
                    public void onSuccess(MessageDetail result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            tvTitle.setText(result.getObj().getTitle());
                            tvContent.setText(result.getObj().getContent());
                            tvTime.setText(result.getObj().getPublish_time());

                        }

                    }

                    @Override
                    public void onError(String e) {

                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }


    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("官方推送")
                .builder();


    }

}
