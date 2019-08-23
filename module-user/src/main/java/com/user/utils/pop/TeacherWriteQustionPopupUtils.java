package com.user.utils.pop;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.fastkit.utils.string_deal.regex.RegexUtils;
import com.lib.utls.share.ShareUtils;
import com.user.R;
import com.zyyoona7.popup.EasyPopup;

public class TeacherWriteQustionPopupUtils {


    private Activity activity;


    public TeacherWriteQustionPopupUtils(Activity activity) {

        this.activity = activity;

        initPopuPopu();
    }

    private EasyPopup popu;


    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_write_question)
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setHeight(WindowManager.LayoutParams.FILL_PARENT)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {

                        initView(view);


                    }

                })
                .setFocusAndOutsideEnable(true)
                .apply();


    }


    private Button btn_sure;
    private TextView tv_url;
    private LinearLayout lin_QQ, lin_weiXin;

    private LinearLayout lin_parent;

    private void initView(View view) {
        lin_parent = view.findViewById(R.id.lin_parent);
        lin_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_sure = view.findViewById(R.id.btn_sure);
        tv_url = view.findViewById(R.id.tv_url);

        lin_QQ = view.findViewById(R.id.lin_QQ);
        lin_weiXin = view.findViewById(R.id.lin_weiXin);

        lin_QQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.getInstance(activity)
                        .setShareWebUrl(tv_url.getText().toString(), "编题连接")
                        .shareQQ();


                dismiss();
            }
        });
        lin_weiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.getInstance(activity)
                        .setShareWebUrl(tv_url.getText().toString(), "编题连接")
                        .shareWEIXIN();
                dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCopyText();
                dismiss();
            }
        });


    }

    public void onCopyText() {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tv_url.getText());
        showToast("复制连接成功!");
    }

    public void setUrl(String url) {
        tv_url.setText(url);

    }

    private void showToast(String a) {
        Toast.makeText(activity, a, Toast.LENGTH_SHORT).show();
    }


    public void dismiss() {
        popu.dismiss();
    }

    public void showAnswerPopuPopu(View view) {
        popu.showAtLocation(view, Gravity.CENTER, 0, 0);


    }

    private TeacherWriteQustionPopupUtils listener;


    public void setWriteMoneyNumUtilsListener(TeacherWriteQustionPopupUtils teacherWriteQustionPopupUtils) {
        this.listener = teacherWriteQustionPopupUtils;


    }


    public interface WriteMoneyNumUtilsListener {

    }


}
