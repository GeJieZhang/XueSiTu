package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.user.R;
import com.user.R2;
import com.user.bean.AddImageBean;
import com.user.view.MyAddImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = ARouterPathUtils.User_HelpActivity)
public class HelpActivity extends BaseAppActivity {
    @BindView(R2.id.et_cmmt)
    AppCompatEditText etCmmt;
    @BindView(R2.id.add1)
    MyAddImageView add1;
    @BindView(R2.id.add2)
    MyAddImageView add2;
    @BindView(R2.id.add3)
    MyAddImageView add3;
    @BindView(R2.id.lin_Image)
    LinearLayout linImage;
    @BindView(R2.id.et_phone)
    AppCompatEditText etPhone;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreateView() {
        initTitle();


        initView();
    }

    private void initView() {
        add1.setMyAddImageViewListener(new MyAddImageView.MyAddImageViewListener() {
            @Override
            public void onClickBg() {
                addImage();
                clickAddImageView = add1;
                clickAddImageViewName = "add1";
            }

            @Override
            public void onClickDelete() {

                deleteImage("add1");

            }
        });

        add2.setMyAddImageViewListener(new MyAddImageView.MyAddImageViewListener() {
            @Override
            public void onClickBg() {
                addImage();
                clickAddImageView = add2;
                clickAddImageViewName = "add2";
            }

            @Override
            public void onClickDelete() {
                deleteImage("add2");
            }
        });
        add3.setMyAddImageViewListener(new MyAddImageView.MyAddImageViewListener() {
            @Override
            public void onClickBg() {
                addImage();
                clickAddImageView = add3;
                clickAddImageViewName = "add3";
            }

            @Override
            public void onClickDelete() {
                deleteImage("add3");
            }
        });
    }

    private void deleteImage(String str) {
        AddImageBean addImageBean = uploadImageMap.get(str);
        MyAddImageView myAddImageView = addImageBean.getMyAddImageView();
        if (myAddImageView != null) {
            myAddImageView.setImageUrl(null);

        }
        uploadImageMap.remove(str);
    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("帮助与反馈")
                .builder();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }


    @OnClick({R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_sure) {
        }


    }

    private MyAddImageView clickAddImageView;

    private String clickAddImageViewName = "add1";

    private void addImage() {
        PhotoUtil.normalSelectPictureByCode(this, new ArrayList<LocalMedia>(), 1, PhotoUtil.ASK_IMAGE_HELP);
    }


    //-----------------------------------------------------------------------------------图片选择回调
    private final int TYPE_IMAGE = 1;
    private Map<String, AddImageBean> uploadImageMap = new HashMap<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.ASK_IMAGE_HELP:

                    // 图片、视频、音频选择结果回调


                    LocalMedia media = PictureSelector.obtainMultipleResult(data).get(0);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    //linImage.removeAllViews();


                    if (media.isCompressed()) {

                        final String compressPath = media.getCompressPath();

                        AddImageBean addImageBean = new AddImageBean();
                        addImageBean.setLocalUrl(compressPath);
                        addImageBean.setMyAddImageView(clickAddImageView);
                        clickAddImageView.setImageUrl(compressPath);
                        uploadImageMap.put(clickAddImageViewName, addImageBean);


                        //上传文件

                        uploadFile(compressPath, TYPE_IMAGE, clickAddImageViewName);


                    }


                    break;

            }


        }
    }


    private void uploadFile(final String compressPath, final int type, final String clickAddImageViewNames) {

        showLog("上传文件路径" + compressPath);

        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(final String s) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (type == TYPE_IMAGE) {

                            AddImageBean addImageBean = uploadImageMap.get(clickAddImageViewNames);

                            if (addImageBean == null) {
                                addImageBean = new AddImageBean();
                            }
                            addImageBean.setUrl(s);
                            uploadImageMap.put(clickAddImageViewNames, addImageBean);

                            showLog("上传图片成功:" + s);

                        }

                    }
                });
            }

            @Override
            public void onError(String e) {

                showLog("文件上传失败:" + e);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (type == TYPE_IMAGE) {
                            AddImageBean addImageBean = uploadImageMap.get(clickAddImageViewNames);

                            MyAddImageView myAddImageView = addImageBean.getMyAddImageView();

                            if (myAddImageView != null) {
                                myAddImageView.setImageUrl(null);

                            }
                            uploadImageMap.remove(clickAddImageViewNames);

                            showToast("选择图片失败！");


                        }

                    }
                });

            }
        });
        qiNiuUploadTask.execute(compressPath, SharedPreferenceManager.getInstance(this).getUserCache().getQiNiuToken());


    }

}
