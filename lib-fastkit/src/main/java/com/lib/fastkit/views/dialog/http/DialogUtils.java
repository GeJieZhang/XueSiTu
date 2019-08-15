package com.lib.fastkit.views.dialog.http;

import android.content.Context;

import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.views.dialog.normal.NormalDialog;
import com.lib.fastkit.views.dialog.tip.QMUITipDialog;


public class DialogUtils {

    QMUITipDialog tipDialog;


    public DialogUtils() {
    }

    public void showNormalDialog(Context context, String str) {

        try {
            tipDialog = new QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(str)
                    .create(true);
            tipDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void dismiss() {


        try {
            tipDialog.dismiss();
            HttpUtils.cancel();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
