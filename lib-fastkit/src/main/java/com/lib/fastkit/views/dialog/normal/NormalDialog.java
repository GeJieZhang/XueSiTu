package com.lib.fastkit.views.dialog.normal;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lib.fastkit.R;
import com.lib.fastkit.utils.file_size.PcUtils;

public class NormalDialog extends DialogFragment {


    private View root;

    private TextView tv_title, tv_content, tv_cancel, tv_sure;

    public static NormalDialog normalDialog;

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });

    }


    private static Builder builder;

    public static AppCompatActivity activity;

    public static Builder getInstance(AppCompatActivity con) {

        builder = new Builder();
        activity = con;
        return builder;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.kit_normal_dialog, container, false);
        tv_title = root.findViewById(R.id.tv_title);
        tv_content = root.findViewById(R.id.tv_content);
        tv_cancel = root.findViewById(R.id.tv_cancel);
        tv_sure = root.findViewById(R.id.tv_sure);

        tv_title.setText(builder.title);
        tv_content.setText(builder.content);
        tv_cancel.setText(builder.cancel);
        tv_sure.setText(builder.sure);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (builder.cancelListener != null) {
                    builder.cancelListener.onCancel(normalDialog);

                }
                dismiss();

            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (builder.surelListener != null) {
                    builder.surelListener.onSure(normalDialog);

                }
                dismiss();

            }
        });


        return root;
    }


    public static class Builder {
        private String title = "请设置标题";
        private String content = "请设置内容！";
        private String cancel = "取消";
        private String sure = "确定";
        private CancelListener cancelListener;
        private SurelListener surelListener;

        public Builder() {


        }

        public Builder setTitle(String title) {

            this.title = title;

            return this;
        }

        public Builder setContent(String content) {

            this.content = content;

            return this;
        }


        public Builder setCancelText(String cancel) {

            this.cancel = cancel;

            return this;
        }

        public Builder setSuerText(String sure) {

            this.sure = sure;

            return this;
        }

        public Builder setCancelListener(CancelListener listener) {

            this.cancelListener = listener;

            return this;
        }

        public Builder setSureListener(SurelListener listener) {

            this.surelListener = listener;

            return this;
        }


        public NormalDialog show() {
            normalDialog = new NormalDialog();
            normalDialog.showNormalDialog();
            return normalDialog;
        }
    }

    private void showNormalDialog() {
        if (!this.isAdded()) {
            activity.getSupportFragmentManager().beginTransaction().add(this, "").commitAllowingStateLoss();
        }

    }


    public interface CancelListener {
        void onCancel(NormalDialog normalDialog);
    }

    public interface SurelListener {
        void onSure(NormalDialog normalDialog);
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

}
