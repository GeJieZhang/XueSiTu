package com.dayi.utils.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.dayi.activity.AskQuestionActivity;
import com.dayi.view.CmmtWordPopup;
import com.lib.fastkit.utils.keyboard.KeyboardUtils;

public class WriteWordPopupUtils {


    private Activity activity;

    public WriteWordPopupUtils(Activity ac) {

        this.activity = ac;

        initCmmtWordPop();
    }

    private CmmtWordPopup cmmtWordPopup;


    private void initCmmtWordPop() {


        cmmtWordPopup = CmmtWordPopup.create(activity)

                .setWordPopupInterface(new CmmtWordPopup.WordPopupInterface() {
                    @Override
                    public void onVoiceClick() {
                        cmmtWordPopup.setText("");
                        if (listener != null) {
                            listener.onVoiceIconClick();
                        }
                        cmmtWordPopup.dismiss();
                    }

                    @Override
                    public void onSendClick(String content) {

                        if (listener != null) {
                            listener.onSendClick(content);
                        }

                        cmmtWordPopup.dismiss();


                    }
                })

                .setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        KeyboardUtils.toggleSoftInput(activity);
                    }
                })
                .apply();

    }

    public void showCmmtWordPop(View view) {
        cmmtWordPopup.showSoftInput().showAtLocation(view, Gravity.BOTTOM, 0, 0);


    }


    private WriteWordPopupUtilsListener listener;

    public void setWriteWordPopupUtilsListener(WriteWordPopupUtilsListener wordPopupUtilsListener) {

        this.listener = wordPopupUtilsListener;

    }

    public void dismiss() {
        cmmtWordPopup.dismiss();
    }

    public interface WriteWordPopupUtilsListener {

        void onVoiceIconClick();

        void onSendClick(String content);


    }


}
