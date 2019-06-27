package com.live.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.herewhite.sdk.AbstractRoomCallbacks;
import com.herewhite.sdk.Environment;
import com.herewhite.sdk.Room;
import com.herewhite.sdk.RoomParams;
import com.herewhite.sdk.WhiteBroadView;
import com.herewhite.sdk.WhiteSdk;
import com.herewhite.sdk.WhiteSdkConfiguration;
import com.herewhite.sdk.domain.Appliance;
import com.herewhite.sdk.domain.DeviceType;
import com.herewhite.sdk.domain.EventEntry;
import com.herewhite.sdk.domain.EventListener;
import com.herewhite.sdk.domain.MemberState;
import com.herewhite.sdk.domain.Promise;
import com.herewhite.sdk.domain.RoomPhase;
import com.herewhite.sdk.domain.RoomState;
import com.herewhite.sdk.domain.SDKError;
import com.herewhite.sdk.domain.UrlInterrupter;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;
import com.live.utils.WhiteBoardUtils;
import com.live.utils.white_board.DemoAPI;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class WhiteBoardFragment extends BaseAppFragment {


    @BindView(R2.id.f_board)
    FrameLayout f_board;

    WhiteBroadView whiteBroadView;

    private Room whiteBroadRoom;

    @SuppressLint("ValidFragment")
    public WhiteBoardFragment(WhiteBroadView whiteBroadView, Room room) {
        this.whiteBroadView = whiteBroadView;
        this.whiteBroadRoom = room;

        showLog("WhiteBoardFragment");
    }

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        whiteBroadView.setLayoutParams(params);


        f_board.addView(whiteBroadView);


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

        f_board.removeAllViews();

    }


    //------------------------------------------------------------------------------------API


    public void getPencil() {

        MemberState memberState = new MemberState();
        memberState.setStrokeColor(new int[]{99, 99, 99});
        memberState.setCurrentApplianceName(Appliance.PENCIL);
        memberState.setStrokeWidth(5);
        memberState.setTextSize(5);

        if (whiteBroadRoom != null) {
            whiteBroadRoom.setMemberState(memberState);
        }


        showLog("设置铅笔");
    }
}
