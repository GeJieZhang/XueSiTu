package com.lib.utls.share;

import android.app.Activity;
import android.content.Context;

import com.lib.fastkit.utils.permission.custom.PermissionUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

public class ShareUtils {


    private ShareAction shareAction;

    private Activity activity;


    public ShareUtils(final Activity activity) {

        this.activity = activity;

        shareAction = new ShareAction(activity);


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
    public ShareUtils setShareWebUrl(String url, String title, String imageUrl, String description) {

        UMWeb umWeb = new UMWeb(url);
        UMImage umImage = new UMImage(activity, imageUrl);
        umWeb.setTitle(title);//标题
        umWeb.setThumb(umImage);  //缩略图
        umWeb.setDescription(description);//描述
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
    public void openShareALLBorad() {

        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN).open();
            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });


    }


    /**
     * 分享新浪
     */
    public void shareSINA() {
        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setPlatform(SHARE_MEDIA.SINA).share();

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });

    }

    /**
     * 分享QQ
     */
    public void shareQQ() {

        PermissionUtil.getInstance(activity).externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                shareAction.setPlatform(SHARE_MEDIA.QQ).share();

            }

            @Override
            public void onRequestPermissionFailure() {

            }
        });



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
}
