package com.xuesitu;

import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.app.ApiUtils;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CacheUtils;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.http.ok.extension.HttpDialogCallBack;
import com.lib.fastkit.db.sample_cache.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.App_TestActivity)
public class TestActivity extends BaseAppActivity {

    @BindView(R.id.btn)
    Button btn;


    @OnClick(R.id.btn)
    public void onViewClicked() {
        HttpUtils.with(this)
                .url(ApiUtils.GET_ALL_CUSTOMER)
                .addParam("personnelId", ACache.get(this).getAsString(CacheUtils.USER_ID))
                .post()
                .execute(new HttpDialogCallBack<String>() {
                    @Override
                    public void onSuccess(String result) {


                    }

                    @Override
                    public void onError(String e) {


                    }
                });

    }


    @Override
    protected void onCreateView() {
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);

    }
}
