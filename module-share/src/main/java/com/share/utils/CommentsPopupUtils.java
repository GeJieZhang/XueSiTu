package com.share.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.lib.fastkit.utils.audio.AudioRecordManager;
import com.lib.fastkit.utils.audio.IAudioRecordListener;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.ui.adapter.BaseAdapter;
import com.zyyoona7.popup.EasyPopup;
import com.share.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommentsPopupUtils implements View.OnClickListener {


    private Activity activity;


    /**
     * 初始化控件
     *
     * @param ac
     */

    private View v_back;
    private TextView tv_comments_num;

    private ImageView iv_delete;


    private SpringView springView;
    private RecyclerView rv;

    private EditText et_message;
    private ImageView iv_delete_message;
    private Button btn_send;


    private HomeAdapter homeAdapter;

    public CommentsPopupUtils(Activity ac) {
        this.activity = ac;

        initVoicePopu();


    }


    //------------------------------------------------------------------------------------------popup

    private EasyPopup voicePopu;


    public void showVoicePopu(View view) {

        voicePopu.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }


    public void dismiss() {
        voicePopu.dismiss();
    }


    private void initVoicePopu() {
        voicePopu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_comments)
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setHeight(WindowManager.LayoutParams.FILL_PARENT)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {


                        initMyView(view);


                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }


    private List<String> list = new ArrayList<>();

    private void initMyView(View view) {

        v_back = view.findViewById(R.id.v_back);
        v_back.setOnClickListener(this);
        tv_comments_num = view.findViewById(R.id.tv_comments_num);
        iv_delete = view.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        springView = view.findViewById(R.id.springView);
        rv = view.findViewById(R.id.rv);
        et_message = view.findViewById(R.id.et_message);
        iv_delete_message = view.findViewById(R.id.iv_delete_message);
        iv_delete_message.setOnClickListener(this);
        btn_send = view.findViewById(R.id.btn_send);


        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");


        homeAdapter = new HomeAdapter(activity, list);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        rv.setAdapter(homeAdapter);


        springView.setHeader(new DefaultHeader(activity));
        springView.setFooter(new DefaultFooter(activity));

        springView.setEnableHeader(false);
        springView.setEnableFooter(true);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {


                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
            }
        });


    }


    private CommentsPopupUtilsListener listener;


    public void setCommentsPopupUtilsListenerr(CommentsPopupUtilsListener CommentsPopupUtilsListener) {

        this.listener = CommentsPopupUtilsListener;

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.v_back) {
            dismiss();

        } else if (i == R.id.iv_delete) {
            dismiss();
        }


    }

    public interface CommentsPopupUtilsListener {

    }


    private class HomeAdapter extends BaseAdapter<String> {

        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_comment;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<String> mData) {

        }
    }


}
