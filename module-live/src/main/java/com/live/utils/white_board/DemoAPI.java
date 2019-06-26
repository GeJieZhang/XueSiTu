package com.live.utils.white_board;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by buhe on 2018/8/16.
 */

public class DemoAPI {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String sdkToken = "WHITEcGFydG5lcl9pZD11QjFvMVhqUjNZa2RxaFpxMWNHTjlNbktBcGNudEtSRWFzNGwmc2lnPWU5ZmZkZjAxYjFjNjE1MmU5NjI0ZGZiNjFkZDRjYWNlMDE5NTIxY2U6YWRtaW5JZD0yNzEmcm9sZT1taW5pJmV4cGlyZV90aW1lPTE1OTMwOTEyMDkmYWs9dUIxbzFYalIzWWtkcWhacTFjR045TW5LQXBjbnRLUkVhczRsJmNyZWF0ZV90aW1lPTE1NjE1MzQyNTcmbm9uY2U9MTU2MTUzNDI1NjYwMjAw";
    private static final String host = "https://cloudcapiv4.herewhite.com";

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    public boolean validateToken() {
        return sdkToken.length() > 200;
    }






    public void createRoom(String name, int limit, Callback callback) {
        Map<String, Object> roomSpec = new HashMap<>();
        roomSpec.put("name", name);
        roomSpec.put("limit", limit);
        RequestBody body = RequestBody.create(JSON, gson.toJson(roomSpec));
        Request request = new Request.Builder()
                .url(host + "/room?token=" + sdkToken)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void getRoomToken(String uuid, Callback callback) {
        Map<String, Object> roomSpec = new HashMap<>();
        RequestBody body = RequestBody.create(JSON, gson.toJson(roomSpec));
        Request request = new Request.Builder()
                .url(host + "/room/join?uuid=" + uuid + "&token=" + sdkToken)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    static String TEST_UUID = "test";
    static String TEST_ROOM_TOKEN = "test";
}
