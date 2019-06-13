package com.lib.fastkit.views.dialog.normal;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lib.fastkit.R;


public class NormalDialog extends DialogFragment {


    private TextView tv_title, tv_content, tv_cancel, tv_sure;
    private String title = "请设置标题";
    private String content = "请设置内容！";
    private String cancel = "取消";
    private String sure = "确定";
    private CancelListener cancelListener;
    private SurelListener surelListener;

    //private  NormalDialog normalDialog;

    public static NormalDialog getInstance() {

//        normalDialog = new NormalDialog();
//        return normalDialog;

        return new NormalDialog();
    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    //
//    public AppCompatActivity activity;
//
////    public static Builder getInstance(AppCompatActivity con) {
////
////        builder = new Builder();
////        activity = con;
////        return builder;
////    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.kit_normal_dialog, container, false);
        tv_title = root.findViewById(R.id.tv_title);
        tv_content = root.findViewById(R.id.tv_content);
        tv_cancel = root.findViewById(R.id.tv_cancel);
        tv_sure = root.findViewById(R.id.tv_sure);

        tv_title.setText(title);
        tv_content.setText(content);
        tv_cancel.setText(cancel);
        tv_sure.setText(sure);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cancelListener != null) {
                    cancelListener.onCancel();

                }
                dismiss();

            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (surelListener != null) {
                    surelListener.onSure();

                }
                dismiss();

            }
        });


        return root;
    }

//
//    public static class Builder {
//        private String title = "请设置标题";
//        private String content = "请设置内容！";
//        private String cancel = "取消";
//        private String sure = "确定";
//        private CancelListener cancelListener;
//        private SurelListener surelListener;
//        private NormalDialog normalDialog;
//
//        private AppCompatActivity activity;
//
//        public Builder(AppCompatActivity con) {
//            this.activity = con;
//
//        }
//
////        public Builder Builder(AppCompatActivity con) {
////
////            this.activity = con;
////
////
////            return this;
////        }
//
//        public Builder setTitle(String title) {
//
//            this.title = title;
//
//            return this;
//        }
//
//        public Builder setContent(String content) {
//
//            this.content = content;
//
//            return this;
//        }
//
//
//        public Builder setCancelText(String cancel) {
//
//            this.cancel = cancel;
//
//            return this;
//        }
//
//        public Builder setSuerText(String sure) {
//
//            this.sure = sure;
//
//            return this;
//        }
//
//        public Builder setCancelListener(CancelListener listener) {
//
//            this.cancelListener = listener;
//
//            return this;
//        }
//
//        public Builder setSureListener(SurelListener listener) {
//
//            this.surelListener = listener;
//
//            return this;
//        }
//
//
//        public NormalDialog show() {
//            normalDialog = new NormalDialog(this, activity);
//            normalDialog.showNormalDialog();
//            return normalDialog;
//        }
//    }

//    private void showNormalDialog() {
//        if (!this.isAdded()) {
//            activity.getSupportFragmentManager().beginTransaction().add(this, "").commitAllowingStateLoss();
//        }
//
//    }

    public NormalDialog setTitle(String title) {

        this.title = title;

        return this;
    }

    public NormalDialog setContent(String content) {

        this.content = content;

        return this;
    }


    public NormalDialog setCancelText(String cancel) {

        this.cancel = cancel;

        return this;
    }

    public NormalDialog setSuerText(String sure) {

        this.sure = sure;

        return this;
    }

    public NormalDialog setCancelListener(CancelListener listener) {

        this.cancelListener = listener;

        return this;
    }

    public NormalDialog setSureListener(SurelListener listener) {

        this.surelListener = listener;

        return this;
    }

    public NormalDialog show(FragmentManager fragmentManager) {
        super.show(fragmentManager, String.valueOf(System.currentTimeMillis()));
        return this;
    }


    public interface CancelListener {
        void onCancel();
    }

    public interface SurelListener {
        void onSure();
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();

    }

}
