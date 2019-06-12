package com.lib.fastkit.views.dialog.http;

import android.content.Context;

import com.lib.fastkit.views.dialog.normal.NormalDialog;
import com.lib.fastkit.views.dialog.tip.QMUITipDialog;


public class DialogUtils {

     QMUITipDialog tipDialog;


    public DialogUtils() {
    }

    public void showNormalDialog(Context context, String str) {
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(str)
                .create();
        tipDialog.setCancelable(true);
        tipDialog.show();

    }

    public  void dismiss() {

        tipDialog.dismiss();

    }


}
