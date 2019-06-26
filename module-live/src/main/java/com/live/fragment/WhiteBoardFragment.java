package com.live.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
import com.live.utils.white_board.DemoAPI;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WhiteBoardFragment extends BaseAppFragment {

    /*和 iOS 名字一致*/
    final String EVENT_NAME = "WhiteCommandCustomEvent";
    private String TEST_UUID = "af35f6aa6b774035a275628416195015";
    DemoAPI demoAPI = new DemoAPI();
    Gson gson = new Gson();
    Room room;
    @BindView(R2.id.white_broad)
    WhiteBroadView whiteBroadView;

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        getRoomToken(TEST_UUID);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_white_board;
    }


    //------------------------------------------------------------------------------------API
    private void getRoomToken(final String uuid) {
        demoAPI.getRoomToken(uuid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showLog("获取房间 token 请求失败:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {

                    if (response.code() == 200) {
                        JsonObject room = gson.fromJson(response.body().string(), JsonObject.class);
                        String roomToken = room.getAsJsonObject("msg").get("roomToken").getAsString();

                        joinRoom(uuid, roomToken);

                    } else {
                        showLog("获取房间 token 失败" + response.body().string());
                    }
                } catch (Throwable e) {
                    showLog("获取房间 token 失败" + e.toString());
                }
            }
        });
    }

    private void joinRoom(String uuid, String roomToken) {


        WhiteSdkConfiguration sdkConfiguration = new WhiteSdkConfiguration(DeviceType.touch, 10, 0.1, true);
        /*显示用户头像*/
        sdkConfiguration.setUserCursor(true);
        /*接受用户头像信息回调，自己实现头像回调。会导致 UserCursor 设置失效。*/
        sdkConfiguration.setCustomCursor(true);

        WhiteSdk whiteSdk = new WhiteSdk(
                whiteBroadView,
                getActivity(),
                sdkConfiguration,
                new UrlInterrupter() {
                    @Override
                    public String urlInterrupter(String sourceUrl) {
                        return sourceUrl;
                    }
                });

        whiteSdk.joinRoom(new RoomParams(uuid, roomToken), new AbstractRoomCallbacks() {
            @Override
            public void onPhaseChanged(RoomPhase phase) {
                showToast(phase.name());

                Log.e("======onPhaseChanged", phase.name());
            }

            @Override
            public void onRoomStateChanged(RoomState modifyState) {


                Log.e("=onRoomStateChanged", gson.toJson(modifyState));
            }
        }, new Promise<Room>() {
            @Override
            public void then(Room wRoom) {

                Log.e("======then", "join in room success");
                room = wRoom;
                addCustomEventListener();

                getPencil();
            }

            @Override
            public void catchEx(SDKError t) {
                showToast(t.getMessage());
            }
        });
    }

    private void addCustomEventListener() {
        room.addMagixEventListener(EVENT_NAME, new EventListener() {
            @Override
            public void onEvent(EventEntry eventEntry) {
                showLog("customEvent payload: " + eventEntry.getPayload().toString());
                showToast(gson.toJson(eventEntry.getPayload()));
            }
        });
    }


    public void getPencil() {

        MemberState memberState = new MemberState();
        memberState.setStrokeColor(new int[]{99, 99, 99});
        memberState.setCurrentApplianceName(Appliance.PENCIL);
        memberState.setStrokeWidth(10);
        memberState.setTextSize(10);
        room.setMemberState(memberState);

        showLog("设置铅笔");
    }
}
