package com.dayi.utils.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dayi.R;
import com.dayi.activity.StudentQuestionListActivity;
import com.dayi.view.CmmtWordPopup;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;
import com.lib.utls.glide.GlideConfig;
import com.luck.picture.lib.photoview.PhotoView;
import com.zyyoona7.popup.EasyPopup;

public class ZoomImagePopupUtils {


    private Activity activity;

    public ZoomImagePopupUtils(Activity ac) {

        this.activity = ac;

        initZoomImagePop();
    }

    private EasyPopup zoomPop;


    private PhotoView iv_photo;

    private ImageView iv_quit;

    private void initZoomImagePop() {
        zoomPop = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_zoom_image)
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setHeight(WindowManager.LayoutParams.FILL_PARENT)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {
                        iv_photo = view.findViewById(R.id.iv_photo);
                        iv_quit = view.findViewById(R.id.iv_quit);
                        iv_quit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                zoomPop.dismiss();
                            }
                        });

                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }

    public void showAnswerPopuPopu(View view) {

        zoomPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    /**
     * interface
     */
    private ZoomImagePopupUtilsListener listener;

    public void setZoomImagePopupUtilsListener(ZoomImagePopupUtilsListener zoomImagePopupUtilsListener) {

        this.listener = zoomImagePopupUtilsListener;

    }

    public void dismiss() {
        zoomPop.dismiss();
    }

    public void setZoomImage(String url) {
        Glide.with(activity)
                .load(url)
                .apply(GlideConfig.getRectangleOptions())
                .into(iv_photo);
    }

    public interface ZoomImagePopupUtilsListener {


    }


}
