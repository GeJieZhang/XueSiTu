package com.youxuan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.ui.fragment.BaseAppFragment;
import com.youxuan.R;
import com.youxuan.R2;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.YouXuan_JianKeFragment)
public class JianKeFragment extends BaseAppFragment {


    @BindView(R2.id.btn_token1)
    Button btnToken1;
    @BindView(R2.id.btn_token2)
    Button btnToken2;
    @BindView(R2.id.btn_token3)
    Button btnToken3;
    @BindView(R2.id.btn_token4)
    Button btnToken4;
    String roomToken1 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:5ugP-WbuM66BYO9I24HVMhhlYeQ=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiIxMzU0MDM1NDU5NyIsImV4cGlyZUF0IjoxNTYyNDY5NDMwLCJwZXJtaXNzaW9uIjoidXNlciJ9";
    String roomToken2 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:9YrKiLh8Cb6SOLIyYQpTP4pqHAI=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiIxMzU0MDM1NDU5NiIsImV4cGlyZUF0IjoxNTYyNDY5NDMwLCJwZXJtaXNzaW9uIjoidXNlciJ9";
    String roomToken3 = "3MREyUAjTV-fOSdRtNpsO3DbNMQVnSdbEyhoNp9q:xf1jHmvqJIHP5apXYOFtKI7G4Pw=:eyJhcHBJZCI6ImU5Yzd1d3RjdCIsInJvb21OYW1lIjoicm9vbTUxMjMiLCJ1c2VySWQiOiIxMzU0MDM1NDU5NSIsImV4cGlyZUF0IjoxNTYyNDY5NDMwLCJwZXJtaXNzaW9uIjoidXNlciJ9";

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_test;
    }


    @OnClick({R2.id.btn_token1, R2.id.btn_token2, R2.id.btn_token3, R2.id.btn_token4})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_token1) {


            PermissionUtil.getInstance(getActivity()).externalZhiBo(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {

                    ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                            .withString("roomToken", roomToken1)
                            .withString("teacherPhone", "13540354597")
                            .withString("roomName", "room5123")
                            .withString("userPhone", "13540354597")

                            .navigation();

                }

                @Override
                public void onRequestPermissionFailure() {

                }
            });
        } else if (i == R.id.btn_token2) {

            PermissionUtil.getInstance(getActivity()).externalZhiBo(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {

                    ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                            .withString("roomToken", roomToken2)
                            .withString("teacherPhone", "13540354597")
                            .withString("roomName", "room5123")
                            .withString("userPhone", "13540354596")
                            .navigation();

                }

                @Override
                public void onRequestPermissionFailure() {

                }
            });

        } else if (i == R.id.btn_token3) {
            PermissionUtil.getInstance(getActivity()).externalZhiBo(new PermissionUtil.RequestPermission() {
                @Override
                public void onRequestPermissionSuccess() {

                    ARouter.getInstance().build(ARouterPathUtils.Live_MainRoomActivity)
                            .withString("roomToken", roomToken3)
                            .withString("teacherPhone", "13540354597")
                            .withString("roomName", "room5123")
                            .withString("userPhone", "13540354595")
                            .navigation();

                }

                @Override
                public void onRequestPermissionFailure() {

                }
            });
        } else if (i == R.id.btn_token4) {
            ARouter.getInstance()
                    .build(ARouterPathUtils.Live_BoardTestActivity)

                    .navigation();
        }
    }
}
