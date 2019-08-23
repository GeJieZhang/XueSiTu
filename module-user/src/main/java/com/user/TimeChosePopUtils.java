package com.user;

import android.content.Context;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lib.fastkit.utils.time_deal.TimeUtils;

import java.util.Calendar;
import java.util.Date;

public class TimeChosePopUtils {

    public TimeChosePopUtils(Context context, boolean year, boolean month, boolean day
            , boolean hour, boolean minute, boolean second) {


        initTimePicker(context, year, month, day, hour, minute, second);


    }


    //------------------------------------------------------------------------------------生日
    private TimePickerView pvTime;

    private void initTimePicker(Context context
            , boolean year, boolean month, boolean day
            , boolean hour, boolean minute, boolean second) {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                String birth = TimeUtils.getDateStr(date, TimeUtils.FORMAT_4);


                if (listener != null) {
                    listener.onTimeChosed(birth);
                }


            }
        })
                .setType(new boolean[]{year, month, day, hour, minute, second})// 默认全部显示
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒

                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("日期选择")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(context.getResources().getColor(R.color.base_title))//标题文字颜色
                .setSubmitColor(context.getResources().getColor(R.color.base_blue))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.base_blue))//取消按钮文字颜色
                .setTitleBgColor(context.getResources().getColor(R.color.white))//标题背景颜色 Night mode
                .setBgColor(context.getResources().getColor(R.color.base_white))//滚轮背景颜色 Night mode
                .setOutSideCancelable(true)
                .build();

        //设置默认时间
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DATE),
                c.get(Calendar.HOUR_OF_DAY) * 0,
                c.get(Calendar.MINUTE) * 0,
                c.get(Calendar.SECOND) * 0);
        pvTime.setDate(c);
    }


    public void show() {
        pvTime.show();


    }

    public void setTitle(String title) {
        pvTime.setTitleText(title);

    }


    private TimeChosePopUtilsListener listener;

    public void setTimeChosePopUtilsListener(TimeChosePopUtilsListener timeChosePopUtilsListener) {


        this.listener = timeChosePopUtilsListener;

    }

    public interface TimeChosePopUtilsListener {
        void onTimeChosed(String str);
    }


}
