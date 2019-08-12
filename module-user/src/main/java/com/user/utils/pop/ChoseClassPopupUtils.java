package com.user.utils.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;

import com.lib.bean.PushDetailBean;
import com.lib.bean.RobQuestionBean;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.framework.component.interceptor.GroupUtils;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.ui.adapter.BaseAdapter;
import com.user.R;
import com.user.bean.ClassBean;
import com.zyyoona7.popup.EasyPopup;

import java.util.ArrayList;
import java.util.List;

public class ChoseClassPopupUtils {


    private Context context;


    public ChoseClassPopupUtils(Context context) {

        this.context = context;

        initPopuPopu();
    }

    private EasyPopup popu;


    private void initPopuPopu() {
        popu = EasyPopup.create()
                .setContext(context)
                .setContentView(R.layout.chose_class_push)
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

    private RecyclerView rv;

    private HomeAdapter homeAdapter;

    private List<ClassBean.ObjBean.RowsBean.LiveRoomBean> list = new ArrayList<>();


    public void updateData(List<ClassBean.ObjBean.RowsBean.LiveRoomBean> list) {

        LogUtil.e("课程数量:" + list.size() + "");
        this.list.clear();
        this.list.addAll(list);
        homeAdapter.notifyDataSetChanged();

    }


    private LinearLayout lin_parent;

    private void initView(View view) {

        rv = view.findViewById(R.id.rv);
        lin_parent = view.findViewById(R.id.lin_parent);
        homeAdapter = new HomeAdapter(context, list);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(homeAdapter);
        lin_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

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


    public class HomeAdapter extends BaseAdapter<ClassBean.ObjBean.RowsBean.LiveRoomBean> {

        public HomeAdapter(Context context, List<ClassBean.ObjBean.RowsBean.LiveRoomBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_chose_myclass;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, final int position, final List<ClassBean.ObjBean.RowsBean.LiveRoomBean> mData) {


            ImageView iv_state = holder.getView(R.id.iv_state);
            TextView tv_state = holder.getView(R.id.tv_state);
            Button btn_sure = holder.getView(R.id.btn_sure);
            holder.setText(R.id.tv_title, mData.get(position).getCourse_name());
            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        listener.onClickId(mData.get(position).getCourse_id());

                        dismiss();

                    }

                }
            });


            if (mData.get(position).getStatus().equals("1")) {

                tv_state.setText("正在开课");
                tv_state.setTextColor(context.getResources().getColor(R.color.base_purple));
                iv_state.setImageResource(R.mipmap.icon_class_on1);

                btn_sure.setBackgroundResource(R.drawable.bg_part_circle1);

            } else {
                tv_state.setText("未开课");

                tv_state.setTextColor(context.getResources().getColor(R.color.base_gray));
                iv_state.setImageResource(R.mipmap.icon_class_off1);

                btn_sure.setBackgroundResource(R.drawable.bg_part_circle3);
            }


        }
    }


    private ChoseClassPopupUtilsListener listener;

    public interface ChoseClassPopupUtilsListener {
        void onClickId(String classId);
    }


    public void setChoseClassPopupUtilsListener(ChoseClassPopupUtilsListener choseClassPopupUtilsListener) {
        this.listener = choseClassPopupUtilsListener;
    }


}
