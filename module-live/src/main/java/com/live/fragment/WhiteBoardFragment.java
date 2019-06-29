package com.live.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.herewhite.sdk.Room;
import com.herewhite.sdk.WhiteBroadView;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;
import com.live.utils.WhiteBoardUtils;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class WhiteBoardFragment extends BaseAppFragment {
    @BindView(R2.id.white_broad)
    WhiteBroadView whiteBroad;


//    @BindView(R2.id.f_board)
//    FrameLayout f_board;


    //WhiteBroadView whiteBroadView;

    // private Room whiteBroadRoom;

    @SuppressLint("ValidFragment")
    public WhiteBoardFragment(WhiteBroadView whiteBroadView, Room room) {
//        this.whiteBroadView = whiteBroadView;
//        this.whiteBroadRoom = room;


        showLog("WhiteBoardFragment");
    }

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        params.width = DisplayUtil.getScreenWidth(getActivity());
//        params.height = DisplayUtil.getScreenHeight(getActivity());
//
//
//
//        showLog("宽度:" + whiteBroadView.getWidth());
//        showLog("高度:" + whiteBroadView.getHeight());
//
//        whiteBroadView.setLayoutParams(params);
        WhiteBoardUtils.getInstance().joinToRoom(getActivity(), whiteBroad);

        // f_board.addView(whiteBroadView);


//        whiteBroadView = view.findViewById(R.id.white_broad);
//
//
//        WhiteBoardUtils.getInstance().joinToRoom(getActivity(), whiteBroadView);

        showLog("onCreateView");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_white_board;
    }

    @Override
    public void onStop() {
        super.onStop();

        //f_board.removeAllViews();

    }


    //------------------------------------------------------------------------------------API


//    public void getPencil() {
//
//        MemberState memberState = new MemberState();
//        memberState.setStrokeColor(new int[]{99, 99, 99});
//        memberState.setCurrentApplianceName(Appliance.PENCIL);
//        memberState.setStrokeWidth(5);
//        memberState.setTextSize(5);
//
//        if (whiteBroadRoom != null) {
//            whiteBroadRoom.setMemberState(memberState);
//        }
//
//
//        showLog("设置铅笔");
//    }
}
