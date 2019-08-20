package com.lib.utls.share;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lib.app.CodeUtil;
import com.lib.bean.ShareBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.utls.pop.SharePopupBottomUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

public class ShareUtils {


    private ShareAction shareAction;

    private Activity activity;

    private String TAG = "=======分享";


    /**
     * ----------------------------------------业务类型
     */

    public static final int BUSINESS_TYPE_EMPTY = 000;
    //晚间陪伴课
    public static final int BUSINESS_TYPE0 = 0;
    //1对1课程
    public static final int BUSINESS_TYPE1 = 1;
    //1对多课程
    public static final int BUSINESS_TYPE2 = 2;

    //1对1体验课
    public static final int BUSINESS_TYPE3 = 3;

    //公开课
    public static final int BUSINESS_TYPE4 = 4;
    //问题
    public static final int BUSINESS_TYPE5 = 5;
    //文章
    public static final int BUSINESS_TYPE6 = 6;

    //老师主页
    public static final int BUSINESS_TYPE7 = 7;
    //话题
    public static final int BUSINESS_TYPE8 = 8;
    //app分享
    public static final int BUSINESS_TYPE9 = 9;

    /**
     * ----------------------------------------分享类型
     */

    //分享到分享圈
    private static final int SHARE_TYPE0 = 0;
    //分享到第三方
    private static final int SHARE_TYPE1 = 1;


    //业务ID(如果是h5就传连接，如果是原生就传id)
    private String business_obj = "0";
    //业务类型
    private int business_type = BUSINESS_TYPE0;

    private int share_type = SHARE_TYPE0;


    private SharePopupBottomUtils sharePopupBottomUtils;

    public ShareUtils(final Activity activity) {

        this.activity = activity;

        shareAction = new ShareAction(activity);
        sharePopupBottomUtils = new SharePopupBottomUtils(activity);

        sharePopupBottomUtils.setSharePopupBottomUtilsListener(sharePopupBottomUtilsListener);
        shareAction.setCallback(umShareListener);


    }


    public static ShareUtils getInstance(Activity activity) {

        return new ShareUtils(activity);
    }


    /**
     * 设置分享文本
     *
     * @param text
     * @return
     */
    public ShareUtils setShareText(String text) {
        shareAction.withText(text);
        return this;
    }


    /**
     * 分享图片
     *
     * @param path
     * @return
     */
    public ShareUtils setShareImage(String path) {
        shareAction.withMedia(new UMImage(activity, path));
        return this;
    }

    /**
     * 设置分享网页
     *
     * @param url
     * @param title
     * @param imageUrl
     * @param description
     * @return
     */
    public ShareUtils setShareWebImageUrl(String url, String title, String imageUrl, String description) {

        UMWeb umWeb = new UMWeb(url);
        UMImage umImage = new UMImage(activity, imageUrl);
        umWeb.setTitle(title);//标题
        umWeb.setThumb(umImage);  //缩略图
        umWeb.setDescription(description);//描述
        shareAction.withMedia(umWeb);
        return this;
    }

    public ShareUtils setShareWebUrl(String url, String title) {

        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);//标题
        shareAction.withMedia(umWeb);
        return this;
    }


    /**
     * 设置分享视频
     *
     * @param url
     * @param title
     * @param imageUrl
     * @param description
     * @return
     */
    public ShareUtils setShareVideo(String url, String title, String imageUrl, String description) {

        UMVideo video = new UMVideo(url);
        UMImage umImage = new UMImage(activity, imageUrl);
        video.setTitle(title);//视频的标题
        video.setThumb(umImage);//视频的缩略图
        video.setDescription(description);//视频的描述
        shareAction.withMedia(video);
        return this;
    }


    /**
     * 设置分享音乐
     *
     * @param urlPlay
     * @param urlJump
     * @param title
     * @param imageUrl
     * @param description
     * @return
     */
    public ShareUtils setShareMusic(String urlPlay, String urlJump, String title, String imageUrl, String description) {

        UMusic music = new UMusic(urlPlay);//音乐的播放链接
        UMImage umImage = new UMImage(activity, imageUrl);
        music.setTitle(title);//音乐的标题
        music.setThumb(umImage);//音乐的缩略图
        music.setDescription(description);//音乐的描述
        music.setmTargetUrl(urlJump);//音乐的跳转链接
        shareAction.withMedia(music);
        return this;
    }


    /**
     * 打开带面板选择的分享
     */
    public ShareAction openShareALLBorad() {

        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN).open();
            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });
        return shareAction;

    }


    public ShareAction onPenCoustomShareBorad() {
        sharePopupBottomUtils.hideCircle(isHideCircle);

        sharePopupBottomUtils.showAnswerPopuPopu(activity.getWindow().getDecorView());


        return shareAction;

    }


    /**
     * 分享新浪
     */
    public ShareAction shareSINA() {
        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setPlatform(SHARE_MEDIA.SINA).share();

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });
        return shareAction;
    }

    /**
     * 分享QQ
     */
    public ShareAction shareQQ() {

        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setPlatform(SHARE_MEDIA.QQ).share();

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });

        return shareAction;


    }

    /**
     * 分享QQ空间
     */
    public ShareAction shareQZONE() {

        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setPlatform(SHARE_MEDIA.QZONE).share();

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });

        return shareAction;


    }

    /**
     * 分享微信
     */
    public void shareWEIXIN() {

        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN).share();

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });


    }

    /**
     * 分享微信朋友圈
     */
    public void shareWEIXIN_CIRCLE() {

        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });


    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

            showLog("分享开始");


        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

            showLog("分享成功");


        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            showLog("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            showLog("分享取消");
        }
    };


    private void requestShareSuccess() {
        HttpUtils.with(activity)
                .addParam("requestType", "SHARE_USER_SHARE")
                .addParam("token", SharedPreferenceManager.getInstance(activity).getUserCache().getUserToken())
                .addParam("business_obj", business_obj)
                .addParam("share_type", share_type)
                .addParam("business_type", business_type)
                .execute(new HttpDialogCallBack<ShareBean>() {
                    @Override
                    public void onSuccess(ShareBean result) {


                        if (result.getCode() != CodeUtil.CODE_200) {
                            Toast.makeText(activity, result.getMsg(), Toast.LENGTH_SHORT).show();

                            return;
                        }


                        switch (ACTION_TYPE) {
                            case CIRCLE: {


                                Toast.makeText(activity, result.getMsg(), Toast.LENGTH_SHORT).show();

                                break;
                            }
                            case QQ: {

                                setShareContent(result);

                                shareQQ();

                                break;
                            }

                            case WEIXIN: {
                                setShareContent(result);
                                shareWEIXIN();

                                break;
                            }
                            case WEIXINCIRCLE: {
                                setShareContent(result);
                                shareWEIXIN_CIRCLE();

                                break;
                            }
                            case SINA: {
                                setShareContent(result);
                                shareSINA();

                                break;
                            }
                        }

                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }

    private void setShareContent(ShareBean result) {
        setShareWebImageUrl(result.getObj().getLink()
                , result.getObj().getTitle()
                , result.getObj().getCover()
                , result.getObj().getDescription()
        );
    }


    private void showLog(String str) {
        Log.e(TAG, str);

    }


    private static final String CIRCLE = "circle";
    private static final String QQ = "QQ";
    private static final String WEIXIN = "WeiXin";
    private static final String WEIXINCIRCLE = "WeiXinCircle";
    private static final String SINA = "sina";

    private String ACTION_TYPE = "circle";

    /**
     * 监听分享布局中的,点击事件
     */
    private SharePopupBottomUtils.SharePopupBottomUtilsListener sharePopupBottomUtilsListener = new SharePopupBottomUtils.SharePopupBottomUtilsListener() {
        @Override
        public void onShareClick() {
            ACTION_TYPE = CIRCLE;
            share_type = SHARE_TYPE0;

            requestShareSuccess();

            sharePopupBottomUtils.dismmiss();


        }

        @Override
        public void onQQClick() {
            ACTION_TYPE = QQ;
            share_type = SHARE_TYPE1;
            requestShareSuccess();
            sharePopupBottomUtils.dismmiss();
        }

        @Override
        public void onWeiXinClick() {
            ACTION_TYPE = WEIXIN;
            share_type = SHARE_TYPE1;
            requestShareSuccess();
            sharePopupBottomUtils.dismmiss();
        }

        @Override
        public void onWeiXinCircleClick() {
            ACTION_TYPE = WEIXINCIRCLE;
            share_type = SHARE_TYPE1;
            requestShareSuccess();
            sharePopupBottomUtils.dismmiss();
        }

        @Override
        public void onWeiBoClick() {
            ACTION_TYPE = SINA;
            share_type = SHARE_TYPE1;
            requestShareSuccess();
            sharePopupBottomUtils.dismmiss();
        }
    };

    //----------------------------------------------------------------------------------（类型）（业务id）


    /**
     * 业务参数
     *
     * @param business_obj
     * @return
     */
    public ShareUtils setShareParams(int business_type, String business_obj) {

        this.business_obj = business_obj;
        this.business_type = business_type;


        return this;
    }


    /**
     * 是否隐藏分享圈
     *
     * @param b
     * @return
     */

    private boolean isHideCircle = false;

    public ShareUtils hideCircle(boolean b) {
        this.isHideCircle = b;

        return this;

    }

}
