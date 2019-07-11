package com.live.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lib.fastkit.utils.px_dp.DisplayUtil;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;
import com.live.activity.MainRoomActivity;
import com.live.bean.live.MyTrackInfo;
import com.qiniu.droid.rtc.QNSurfaceView;
import com.qiniu.droid.rtc.QNTrackInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListVideoFragment extends BaseAppFragment {


    //横屏
    public int screenHorization = Configuration.ORIENTATION_LANDSCAPE;
    //竖屏
    public int screenVertical = Configuration.ORIENTATION_PORTRAIT;
    @BindView(R2.id.lin_whiteboard)
    LinearLayout linWhiteboard;
    @BindView(R2.id.lin_ppt)
    LinearLayout linPpt;
    @BindView(R2.id.rv_list_video)
    RecyclerView rvListVideo;
    @BindView(R2.id.lin_parent)
    LinearLayout lin_parent;


    private HomeAdapter homeAdapter;

    private LinearLayoutManager linearLayoutManager;


    private List<MyTrackInfo> list = new ArrayList<>();

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        initRecyclerView();

        //LogUtil.e("onCreateView");

    }


    @Override
    public void onResume() {
        super.onResume();

        refreshLinearLayoutManager();


        setQNSurfaceView();


    }

    private void refreshLinearLayoutManager() {
        if (MainRoomActivity.screenOrientation == screenVertical) {
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        } else {

            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
    }

    private void setQNSurfaceView() {

        homeAdapter.notifyDataSetChanged();

    }

    public void initRecyclerView() {

        if (rvListVideo != null) {

            homeAdapter = new HomeAdapter(getActivity(), list);
            linearLayoutManager = new LinearLayoutManager(getActivity());


            refreshLinearLayoutManager();

            rvListVideo.setLayoutManager(linearLayoutManager);
            rvListVideo.setAdapter(homeAdapter);
        }


    }

    @Override
    protected int getLayoutId() {

        if (MainRoomActivity.screenOrientation == screenVertical) {

            return R.layout.item_horizontal;

        } else {
            return R.layout.item_vertical;
        }

        //return R.layout.fragment_list_video;
    }

    @OnClick({R2.id.lin_ppt, R2.id.lin_whiteboard})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_ppt) {

            if (listener != null) {
                listener.onPPTClick();
            }


        } else if (i == R.id.lin_whiteboard) {
            if (listener != null) {
                listener.onWhiteBoradClick();
            }

        }
    }


    private List<QNSurfaceView> qnSurfaceViewList = new ArrayList<>();

    public class HomeAdapter extends BaseAdapter<MyTrackInfo> {

        public HomeAdapter(Context context, List<MyTrackInfo> mData) {
            super(context, mData);


        }

        @Override
        public int getLayoutId() {


            return R.layout.item_list_video;
        }

        @Override
        protected void toBindViewHolder(final ViewHolder holder, final int position, List<MyTrackInfo> mData) {


            setParentSize(holder);

            holder.setText(R.id.tv_userName, mData.get(position).getUserName());

            final QNSurfaceView qn_video = holder.getView(R.id.qn_video);
            qn_video.setZOrderOnTop(false);
            qn_video.setZOrderMediaOverlay(false);

            qnSurfaceViewList.add(qn_video);


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
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) linearLayout.getLayoutParams();
            if (MainRoomActivity.screenOrientation == screenVertical) {
                int margin = DisplayUtil.dip2px(getActivity(), 4);
                params.setMargins(margin, 0, margin, 0);


            } else {

            }

            linearLayout.setLayoutParams(params);
        }
    }


    public void setTrackInfo(List<MyTrackInfo> trackInfoList) {


        list.clear();
        list.addAll(trackInfoList);
        if (homeAdapter != null) {
            homeAdapter.notifyDataSetChanged();
        }

    }


    public interface ListVideoFragmentListener {

        void onFindAdmin(MyTrackInfo trackInfo);


        void onSetQNSurfaceView(MyTrackInfo trackInfo, QNSurfaceView qnSurfaceView);

        void onChangeQNSurfaceView(MyTrackInfo trackInfo, QNSurfaceView qnSurfaceView);


        void onWhiteBoradClick();

        void onPPTClick();

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


    public void setQnSurfaceViewTop(boolean b) {

        for (QNSurfaceView qnSurfaceView : qnSurfaceViewList) {
            qnSurfaceView.setZOrderOnTop(b);
            qnSurfaceView.setZOrderMediaOverlay(b);
        }


    }

    public void hideVideoList(boolean b) {

        if (b) {
            rvListVideo.removeAllViews();
            //rvListVideo.setVisibility(View.GONE);

        } else {
            // rvListVideo.setVisibility(View.VISIBLE);
        }

    }

}
