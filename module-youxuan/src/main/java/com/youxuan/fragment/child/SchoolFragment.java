package com.youxuan.fragment.child;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.fastkit.views.recyclerview.tool.MyLinearLayoutManager;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.glide.GlideConfig;
import com.youxuan.R;
import com.youxuan.R2;
import com.youxuan.bean.YouXuanBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
@Route(path = ARouterPathUtils.YouXuan_SchoolFragment)
public class SchoolFragment extends BaseAppFragment {
    @BindView(R2.id.rv_school)
    RecyclerView rvSchool;


    private HomeAdapter homeAdapter;

    private List<YouXuanBean.ObjBean.StageBean.CourseBean> carList = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public SchoolFragment(List<YouXuanBean.ObjBean.StageBean.CourseBean> list) {

        carList.clear();
        carList.addAll(list);


    }

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        initView();

    }

    private void initView() {
        homeAdapter = new HomeAdapter();
        rvSchool.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        rvSchool.setNestedScrollingEnabled(false);
        rvSchool.setAdapter(homeAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_school;
    }


    private static final int VIEW_TYPE = -1;


    class HomeAdapter extends RecyclerView.Adapter<ViewHolder> {


        public HomeAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            if (viewType == VIEW_TYPE) {
                holder = ViewHolder.createViewHolder(getActivity(), parent, R.layout.base_empty);
            } else {
                holder = ViewHolder.createViewHolder(getActivity(), parent, R.layout.item_hudong2);
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


            if (getItemViewType(position) == VIEW_TYPE) {
                return;
            } else {
                //String teacher[] = {"曹老师", "张老师", "李老师"};

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(ARouterPathUtils.YouXuan_NormalDetailWebActivity)
                                .withString("urlPath", carList.get(position).getCourse_link())
                                .navigation();
                    }
                });

                holder.setText(R.id.tv_title, carList.get(position).getCourse_name());

                holder.setText(R.id.tv_remaining, "剩余名额:" + carList.get(position).getRemaining());

                holder.setText(R.id.tv_ks, "课时数:" + carList.get(position).getTotal_class());
                holder.setText(R.id.tv_money, carList.get(position).getPrice() + "");
                setIconType(holder, position);


                setRemainingTime(holder, position);


                LinearLayout lin_head = holder.getView(R.id.lin_head);
                lin_head.removeAllViews();
                final List<YouXuanBean.ObjBean.StageBean.CourseBean.TeacherInfoBean> teacherInfoBeanList = carList.get(position).getTeacher_info();


                for (int i = 0; i < teacherInfoBeanList.size(); i++) {

                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_teacher, null);


                    final ImageView iv_king = view.findViewById(R.id.iv_king);

                    if (i != 0) {
                        iv_king.setVisibility(View.GONE);
                    }

                    final TextView tv_name = view.findViewById(R.id.tv_name);

                    tv_name.setText(teacherInfoBeanList.get(i).getTeacher_name());

                    final ImageView iv_head = view.findViewById(R.id.iv_head);

                    final int finalI = i;
                    iv_head.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance().build(ARouterPathUtils.YouXuan_NormalDetailWebActivity)
                                    .withString("urlPath", teacherInfoBeanList.get(finalI).getTeacher_link())
                                    .navigation();
                        }
                    });

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, DisplayUtil.dip2px(getActivity(), 16), 0);
                    view.setLayoutParams(params);
                    lin_head.addView(view);

                    Glide.with(getActivity())
                            .load(teacherInfoBeanList.get(i).getTeacher_photo_url())
                            .apply(GlideConfig.getCircleOptions())
                            .into(iv_head);


                }


            }
        }


        @Override
        public int getItemCount() {
            //返回集合的长度
            return carList.size() > 0 ? carList.size() : 1;
        }


        /**
         * 获取条目 View填充的类型
         * 默认返回0
         * 将lists为空返回-1
         *
         * @param position
         * @return
         */
        public int getItemViewType(int position) {
            if (carList.size() <= 0) {
                return VIEW_TYPE;
            }
            return super.getItemViewType(position);
        }

    }

    private void setIconType(ViewHolder holder, int position) {
        //0-闪电班，1-提升班，2-衔接班
        String type = carList.get(position).getCourse_type();
        ImageView iv_type = holder.getView(R.id.iv_type);
        switch (type) {
            case "0": {
                iv_type.setImageResource(R.mipmap.icon_lightning);

                break;
            }
            case "1": {
                iv_type.setImageResource(R.mipmap.icon_promote);
                break;
            }
            case "2": {

                iv_type.setImageResource(R.mipmap.icon_connect);
                break;
            }
        }
    }

    private void setRemainingTime(ViewHolder holder, int position) {
        String timeCut = TimeUtils.getRemainingTime(carList.get(position).getCutoff_date(), TimeUtils.FORMAT_0);


        //showLog(timeCut);
        if (!timeCut.equals("0")) {
            holder.setText(R.id.tv_time1, String.valueOf(timeCut.charAt(0)));
            holder.setText(R.id.tv_time2, String.valueOf(timeCut.charAt(1)));
            holder.setText(R.id.tv_time3, String.valueOf(timeCut.charAt(3)));
            holder.setText(R.id.tv_time4, String.valueOf(timeCut.charAt(4)));
            holder.setText(R.id.tv_time5, String.valueOf(timeCut.charAt(6)));
            holder.setText(R.id.tv_time6, String.valueOf(timeCut.charAt(7)));
        } else {

            holder.getView(R.id.include_time).setVisibility(View.GONE);
        }


    }


    public void updateRemainingTime() {

        if (homeAdapter != null) {
            homeAdapter.notifyDataSetChanged();
        }


        // showLog("刷新活动结束日期!");
    }
}
