package com.live.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;
import com.live.activity.MainRoomActivity;
import com.qiniu.droid.rtc.QNSurfaceView;
import com.qiniu.droid.rtc.QNTrackInfo;

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


    private List<QNTrackInfo> list = new ArrayList<>();

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        initRecyclerView();

        //LogUtil.e("onCreateView");

    }


    @Override
    public void onResume() {
        super.onResume();

//        if (MainRoomActivity.screenOrientation == screenVertical) {
//            ViewGroup.LayoutParams params = hScrollView.getLayoutParams();
//            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            hScrollView.setLayoutParams(params);
//        } else {
//            ViewGroup.LayoutParams params = hScrollView.getLayoutParams();
//            params.width = DisplayUtil.dip2px(getActivity(), 290);
//            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            hScrollView.setLayoutParams(params);
//
//        }


        setQNSurfaceView();


    }

    private void setQNSurfaceView() {

        homeAdapter.notifyDataSetChanged();

    }

    public void initRecyclerView() {

        if (rvListVideo != null) {

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


    //private List<QNSurfaceView> qnSurfaceViewList = new ArrayList<>();

    public class HomeAdapter extends BaseAdapter<QNTrackInfo> {

        public HomeAdapter(Context context, List<QNTrackInfo> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_list_video;
        }

        @Override
        protected void toBindViewHolder(final ViewHolder holder, final int position, List<QNTrackInfo> mData) {


            //setParentSize(holder);

            final QNSurfaceView qn_video = holder.getView(R.id.qn_video);
            qn_video.setZOrderOnTop(false);
            qn_video.setZOrderMediaOverlay(false);

            if (position == 0) {

                holder.setVisibility(false);

                if (listener != null) {
                    listener.onFindAdmin(list.get(position));
                }

            } else {

                if (listener != null) {
                    listener.onSetQNSurfaceView(list.get(position), qn_video);
                }

            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        listener.onChangeQNSurfaceView(list.get(position), qn_video);
                    }


                }
            });


        }


        /**
         * 设置父容器的大小
         *
         * @param holder
         */
        private void setParentSize(ViewHolder holder) {
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


    public void setTrackInfo(List<QNTrackInfo> trackInfoList) {

        list.clear();
        list.addAll(trackInfoList);
        if (homeAdapter != null) {
            homeAdapter.notifyDataSetChanged();
        }

    }


    public interface ListVideoFragmentListener {

        void onFindAdmin(QNTrackInfo trackInfo);


        void onSetQNSurfaceView(QNTrackInfo trackInfo, QNSurfaceView qnSurfaceView);

        void onChangeQNSurfaceView(QNTrackInfo trackInfo, QNSurfaceView qnSurfaceView);

    }

    private ListVideoFragmentListener listener;

    public void setListVideoFragmentListener(ListVideoFragmentListener listVideoFragmentListener) {

        this.listener = listVideoFragmentListener;

    }

    @Override
    public void onPause() {
        super.onPause();
        //LogUtil.e("onPause");
    }

    @Override
    public void onDestroyView() {

        //LogUtil.e("onDestroyView");
        super.onDestroyView();
    }


}
