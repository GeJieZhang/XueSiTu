package com.live.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.herewhite.sdk.AbstractRoomCallbacks;
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
import com.herewhite.sdk.domain.ViewMode;
import com.lib.fastkit.views.dialog.normal.NormalDialog;
import com.live.utils.white_board.DemoAPI;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WhiteBoardUtils {

    /*和 iOS 名字一致*/
    final String EVENT_NAME = "WhiteCommandCustomEvent";
    private String TEST_UUID = "b63e8b7eee3045f6afbedf53364a17b9";
    DemoAPI demoAPI;
    Gson gson;
    Room room;

    private WhiteBroadView whiteBroadView;

    private Activity activity;

    public WhiteBoardUtils() {

        demoAPI = new DemoAPI();
        gson = new Gson();
    }

    public static WhiteBoardUtils getInstance() {

        return new WhiteBoardUtils();
    }


    public Room joinToRoom(Activity activity, WhiteBroadView broadView) {
        this.activity = activity;
        this.whiteBroadView = broadView;

        return getRoomToken(TEST_UUID);

    }

    private Room getRoomToken(final String uuid) {
        final Room[] room1 = new Room[1];

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

                        room1[0] = joinRoom(uuid, roomToken);

                    } else {
                        showLog("获取房间 token 失败" + response.body().string());
                    }
                } catch (Throwable e) {
                    showLog("获取房间 token 失败" + e.toString());
                }
            }
        });

        return room1[0];
    }


    private Room joinRoom(String uuid, String roomToken) {


        WhiteSdkConfiguration sdkConfiguration = new WhiteSdkConfiguration(DeviceType.touch, 10, 0.1, true);
        /*显示用户头像*/
        sdkConfiguration.setUserCursor(true);
        /*接受用户头像信息回调，自己实现头像回调。会导致 UserCursor 设置失效。*/
        sdkConfiguration.setCustomCursor(true);
        WhiteSdk whiteSdk = new WhiteSdk(
                whiteBroadView,
                activity,
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
                showLog(phase.name());


            }

            @Override
            public void onRoomStateChanged(RoomState modifyState) {

                showLog(gson.toJson(modifyState));

            }
        }, new Promise<Room>() {
            @Override
            public void then(Room wRoom) {


                showLog("加入画板房间成功!");

                System.out.print("加入画板房间成功!");
                room = wRoom;
                addCustomEventListener();
                getPencil();

            }

            @Override
            public void catchEx(SDKError t) {


                showLog(t.getMessage());
            }
        });

        return room;
    }

    private void addCustomEventListener() {
        room.addMagixEventListener(EVENT_NAME, new EventListener() {
            @Override
            public void onEvent(EventEntry eventEntry) {
                showLog("customEvent payload: " + eventEntry.getPayload().toString());

            }
        });
    }

    /**
     * Log
     *
     * @param msg
     */
    public static void showLog(final String msg) {

        Log.e("调试日志======", msg);
    }

    public void getPencil() {

        MemberState memberState = new MemberState();
        memberState.setStrokeColor(new int[]{99, 99, 99});
        memberState.setCurrentApplianceName(Appliance.PENCIL);
        memberState.setStrokeWidth(10);
        memberState.setTextSize(10);


        room.setMemberState(memberState);

        room.setViewMode(ViewMode.Broadcaster);

        room.zoomChange(1);

        showLog("设置铅笔");
    }

}
