package com.live.utils.socket;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.lib.fastkit.utils.log.LogUtil;
import com.live.utils.WhiteBoardUtils;
import com.live.utils.white_board.DemoAPI;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class IMSocketUtils {

    private static IMSocketUtils instance;

    private WebSocket mSocket;

    private boolean isClose = false;
    private boolean isReallyClose = false;

    private final String socketPath = "ws://192.168.0.117:8081/websocket/";

    private String path;


    public IMSocketUtils() {

    }

    public static synchronized IMSocketUtils getInstance() {

        if (instance == null) {
            instance = new IMSocketUtils();
        }
        return instance;
    }


    public IMSocketUtils start(String roomName, String token) {
        path = socketPath + roomName + "/" + token;

        LogUtil.e("长连接" + path);

        startConnection();

        return instance;
    }

    private void startConnection() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Request request = new Request.Builder().url(path).build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            mSocket = webSocket;
            isClose = false;
            LogUtil.e("长连接-连接成功！");


            if (listener != null) {
                listener.onOpen();
            }


            heartMessage();


        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            isClose = false;
            //LogUtil.e("长连接-receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, final String text) {
            super.onMessage(webSocket, text);
            LogUtil.e("长连接-receive text:" + text);

            isClose = false;


            if (listener != null) {
                listener.onMessage(text);
            }


        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            LogUtil.e("长连接-closed:" + reason);

            if (!isReallyClose) {
                isClose = true;
            }

            handler.postDelayed(runnable, 3000);
            if (listener != null) {
                listener.onClosed();
            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            LogUtil.e("长连接-closing:" + reason);
            if (!isReallyClose) {
                isClose = true;
            }
            handler.postDelayed(runnable, 3000);
            if (listener != null) {
                listener.onClosing();
            }
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            LogUtil.e("长连接-failure:" + t.getMessage());

            if (!isReallyClose) {
                isClose = true;
            }
            handler.postDelayed(runnable, 3000);
            if (listener != null) {
                listener.onFailure();
            }
        }
    }

    //--------------------------------------------------------------------------------------发送消息

    private Handler handler = new Handler();

    private void heartMessage() {
        count = 0;
        handler.postDelayed(runnable, 1000);

    }


    private int count = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isClose) {

                if (count <= 3) {
                    startConnection();
                    LogUtil.e("重新连接");
                    count++;
                }

                return;
            }
            mSocket.send("");
            LogUtil.e("心跳包");
            handler.postDelayed(runnable, 1000 * 60 * 5);


        }
    };

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

//    private void heartMessage() {
//        final String message = "";
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                mSocket.send(message);
//
//                LogUtil.e("心跳包");
//            }
//        }, 1000);
//    }

    public void sendMessage(String message) {

        if (mSocket != null) {
            mSocket.send(message);
        }


    }

    //--------------------------------------------------------------------------------------接口回调


    private IMSocketListener listener;

    public interface IMSocketListener {
        void onOpen();

        void onMessage(String json);

        void onClosed();

        void onClosing();

        void onFailure();
    }

    public IMSocketUtils setIMSocketListener(IMSocketListener imSocketListener) {

        this.listener = imSocketListener;

        return instance;

    }


    public void closeSocket() {

        if (mSocket != null) {
            mSocket.close(1000, "断开IMSocket");
        }


        isReallyClose = true;

        handler.removeCallbacks(runnable);

    }

}
