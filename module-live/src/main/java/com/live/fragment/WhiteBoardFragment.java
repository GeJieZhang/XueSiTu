package com.live.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.herewhite.sdk.Room;
import com.herewhite.sdk.WhiteBroadView;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class WhiteBoardFragment extends BaseAppFragment {
//    @BindView(R2.id.white_broad)
//    WhiteBroadView whiteBroad;


    @BindView(R2.id.f_board)
    FrameLayout f_board;


    WhiteBroadView whiteBroad;

    Room room;


    @SuppressLint("ValidFragment")
    public WhiteBoardFragment(WhiteBroadView whiteBroadView, Room whiteBoardRoom) {
        this.whiteBroad = whiteBroadView;

        this.room = whiteBoardRoom;

        showLog("WhiteBoardFragment");


    }

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        f_board.setLayoutParams(params);
        f_board.addView(whiteBroad);


        showLog("onCreateView");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_white_board;
    }

    @Override
    public void onStop() {
        super.onStop();

        f_board.removeAllViews();

    }

    @Override
    public void onResume() {
        super.onResume();
        showLog("onResume");


        refreshRoom();


    }

    private void refreshRoom() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (room != null) {
                    showLog("刷新白板");
                    room.refreshViewSize();


                }
            }
        }, 1000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

//        if (room != null) {
//            showLog("刷新白板");
//            room.refreshViewSize();
//
//
//        }
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

    public void setRoom(Room room) {
        this.room = room;
        refreshRoom();
    }
}
