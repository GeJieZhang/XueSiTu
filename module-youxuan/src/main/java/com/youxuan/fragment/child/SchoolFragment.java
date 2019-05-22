package com.youxuan.fragment.child;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.recyclerview.tool.MyLinearLayoutManager;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.fragment.BaseAppFragment;
import com.youxuan.R;
import com.youxuan.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = ARouterPathUtils.YouXuan_SchoolFragment)
public class SchoolFragment extends BaseAppFragment {
    @BindView(R2.id.rv_school)
    RecyclerView rvSchool;


    private HomeAdapter homeAdapter;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        initView();

    }

    private void initView() {
        carList.add("");
        carList.add("");
        carList.add("");
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
    private List<String> carList = new ArrayList<>();


    class HomeAdapter extends RecyclerView.Adapter<ViewHolder> {


        public HomeAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            if (viewType == VIEW_TYPE) {
                holder = ViewHolder.createViewHolder(getActivity(), parent, R.layout.item_hudong2);
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
                String teacher[] = {"曹老师", "张老师", "李老师"};

                LinearLayout lin_head = holder.getView(R.id.lin_head);

                for (int i = 0; i < teacher.length; i++) {

                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_teacher, null);

                    final TextView tv_title = view.findViewById(R.id.tv_name);
                    tv_title.setText(teacher[i]);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, DisplayUtil.dip2px(getActivity(), 16), 0);
                    view.setLayoutParams(params);
                    lin_head.addView(view);


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
}
