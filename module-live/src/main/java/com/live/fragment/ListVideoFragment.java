package com.live.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;
import com.live.activity.MainRoomActivity;
import com.live.activity.RoomActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListVideoFragment extends BaseAppFragment {
    @BindView(R2.id.rv_list_video)
    RecyclerView rvListVideo;


    //横屏
    public int screenHorization = Configuration.ORIENTATION_LANDSCAPE;
    //竖屏
    public int screenVertical = Configuration.ORIENTATION_PORTRAIT;


    private HomeAdapter homeAdapter;

    private LinearLayoutManager linearLayoutManager;


    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        initRecyclerView();

        LogUtil.e("onCreateView");

    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("onResume");
        if (MainRoomActivity.screenOrientation == screenVertical) {

            if (linearLayoutManager != null) {

                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            }

        } else {

            if (linearLayoutManager != null) {
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


            }

        }
    }

    public void initRecyclerView() {

        if (rvListVideo != null) {
            list.clear();
            list.add("");
            list.add("");
            homeAdapter = new HomeAdapter(getActivity(), list);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvListVideo.setLayoutManager(linearLayoutManager);
            rvListVideo.setAdapter(homeAdapter);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_video;
    }


    public class HomeAdapter extends BaseAdapter<String> {

        public HomeAdapter(Context context, List<String> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_list_video;
        }

        @Override
        protected void toBindViewHolder(final ViewHolder holder, int position, List<String> mData) {

            LinearLayout linearLayout = holder.getView(R.id.item_parent);


            ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
            if (MainRoomActivity.screenOrientation == screenVertical) {
                params.width = DisplayUtil.dip2px(getActivity(), 100);
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;


            } else {
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = DisplayUtil.dip2px(getActivity(), 100);
            }

            linearLayout.setLayoutParams(params);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e("onPause");
    }

    @Override
    public void onDestroyView() {

        LogUtil.e("onDestroyView");
        super.onDestroyView();
    }


}
