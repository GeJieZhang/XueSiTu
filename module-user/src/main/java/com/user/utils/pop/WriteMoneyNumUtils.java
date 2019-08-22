package com.user.utils.pop;

import android.content.Context;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lib.fastkit.utils.share.tool.StringUtil;
import com.lib.fastkit.utils.string_deal.regex.RegexUtils;
import com.user.R;
import com.zyyoona7.popup.EasyPopup;

public class WriteMoneyNumUtils {


    private Context context;


    public WriteMoneyNumUtils(Context context) {

        this.context = context;

        initPopuPopu();
    }

    private EasyPopup popu;


    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(context)
                .setContentView(R.layout.popup_write_money_num)
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


    private ImageView iv_delete;
    private EditText et_num;
    private Button btn_sure;

    private void initView(View view) {
        iv_delete = view.findViewById(R.id.iv_delete);
        et_num = view.findViewById(R.id.et_num);

        btn_sure = view.findViewById(R.id.btn_sure);

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String num = et_num.getText().toString();


                if (RegexUtils.check10Num(context, num)) {
                    if (listener != null) {
                        listener.onMumberSure(Integer.parseInt(num));
                        dismiss();
                    }
                } else {

                    showToast("请输入10的倍数");

                }


            }
        });


    }


    /**
     * 设置文字
     *
     * @param num
     */
    public void setEt_num(double num) {


        if (et_num != null) {

            if (num != 0) {
                et_num.setText(num + "");
            }

        }


    }

    private void showToast(String a) {
        Toast.makeText(context, a, Toast.LENGTH_SHORT).show();
    }


    public void dismiss() {
        popu.dismiss();
    }

    public void showAnswerPopuPopu(View view) {
        popu.showAtLocation(view, Gravity.CENTER, 0, 0);


    }

    private WriteMoneyNumUtilsListener listener;


    public void setWriteMoneyNumUtilsListener(WriteMoneyNumUtilsListener writeMoneyNumUtilsListener) {
        this.listener = writeMoneyNumUtilsListener;


    }


    public interface WriteMoneyNumUtilsListener {
        void onMumberSure(int num);
    }


}
