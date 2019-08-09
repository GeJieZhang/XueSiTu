package com.live.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.herewhite.sdk.Room;
import com.herewhite.sdk.WhiteBroadView;
import com.herewhite.sdk.domain.Appliance;
import com.herewhite.sdk.domain.ImageInformationWithUrl;
import com.herewhite.sdk.domain.MemberState;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.utils.color.ColorUtil;
import com.lib.fastkit.utils.json_deal.lib_mgson.MGson;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.ui.fragment.BaseAppFragment;
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.live.R;
import com.live.R2;
import com.live.activity.MainRoomActivity;
import com.live.bean.control.IMBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class WhiteBoardFragment extends BaseAppFragment {
//    @BindView(R2.id.white_broad)
//    WhiteBroadView whiteBroad;


    @BindView(R2.id.f_board)
    FrameLayout f_board;
    @BindView(R2.id.iv_font)
    ImageView ivFont;
    @BindView(R2.id.iv_next)
    ImageView ivNext;
    @BindView(R2.id.tv_page_num)
    TextView tvPageNum;
    @BindView(R2.id.lin_insert)
    LinearLayout lin_insert;
    @BindView(R2.id.tv_progress)
    TextView tv_progress;

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
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        f_board.setLayoutParams(params);
        f_board.addView(whiteBroad);


        showLog("onCreateView" + "Width:" + whiteBroad.getWidth() + "Height:" + whiteBroad.getHeight());


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


    public void refreshRoom() {
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


    public void setRoom(Room room) {
        this.room = room;


        refreshRoom();
    }


    @OnClick({R2.id.iv_font, R2.id.iv_next})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_font) {

            if (listener != null) {
                listener.onWihteBoradFont();
            }


        } else if (i == R.id.iv_next) {

            if (listener != null) {
                listener.onWihteBoradNext();
            }
        }


    }

    public void setBoradTool(String pencil) {
        toolName = pencil;
        setTool();
        MainRoomActivity.whiteBoradBean.initValue();


        switch (pencil) {
            case Appliance.PENCIL:
                MainRoomActivity.whiteBoradBean.setPen(true);

                break;

            case Appliance.ERASER:
                MainRoomActivity.whiteBoradBean.setEraser(true);

                break;

            case Appliance.RECTANGLE:
                MainRoomActivity.whiteBoradBean.setRectangular(true);

                break;
            case Appliance.SELECTOR:
                //移动工具不做处理


                break;

            case Appliance.ELLIPSE:

                MainRoomActivity.whiteBoradBean.setCirle(true);

                break;


        }


        MainRoomActivity.roomControlFragment.initToolIcon();
    }

    /**
     * 设置画笔粗细
     *
     * @param progress
     */
    public void setPenProgress(int progress) {
        toolStrokeWidth = progress;
        setTool();
        MainRoomActivity.whiteBoradBean.setPenSize(progress);
    }

    /**
     * @param color    颜色id
     * @param position 颜色索引
     */
    public void setPenColor(Integer color, int position) {
        toolColor = color;
        setTool();
        MainRoomActivity.whiteBoradBean.setCirlerIndex(position);
    }


    public interface WhiteBoardFragmentListener {
        void onWihteBoradFont();

        void onWihteBoradNext();
    }


    private WhiteBoardFragment.WhiteBoardFragmentListener listener;

    public void setWhiteBoardFragmentListener(WhiteBoardFragment.WhiteBoardFragmentListener whiteBoardFragmentListener) {

        this.listener = whiteBoardFragmentListener;

    }


    public void updatePage(String page) {

        tvPageNum.setText(page);

    }


    /**
     * 插入图片
     * RoomControlFragment调用
     */
    List<LocalMedia> listImage = new ArrayList<>();

    public void insertImage() {

        PhotoUtil.normalSelectPictureByCode(getActivity(), listImage, 1, PhotoUtil.WHITE_BORAD_IMAGE);


    }


    /**
     * 上传图片操作
     * MainRoomActivity调用
     *
     * @param compressPath
     */
    public void upLoadImage(String compressPath) {

        lin_insert.setVisibility(View.VISIBLE);

        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {

                tv_progress.setText(progress + "%");


            }

            @Override
            public void onSuccess(String s) {

                lin_insert.setVisibility(View.GONE);

                String urlHead = SharedPreferenceManager.getInstance(getContext()).getUserCache().getQiNiuUrl();

                room.insertImage(new ImageInformationWithUrl(0d, 0d, 200d, 100d, urlHead + s));
            }

            @Override
            public void onError(String e) {

                lin_insert.setVisibility(View.GONE);

                showToast("图片上传失败");
            }
        });
        qiNiuUploadTask.execute(compressPath, SharedPreferenceManager.getInstance(getActivity()).getUserCache().getQiNiuToken());
    }

    //------------------------------------------------------------------------------------API

    private int toolStrokeWidth = 5;

    private int toolTextSize = 5;

    private String toolName = Appliance.PENCIL;

    private int toolColor = R.color.base_money;

    public void setTool() {

        Room room = MainRoomActivity.getwhiteBoardRoom();
        MemberState memberState = new MemberState();
        memberState.setStrokeColor(ColorUtil.int2Rgb(getResources().getColor(toolColor)));
        memberState.setCurrentApplianceName(toolName);
        memberState.setStrokeWidth(toolStrokeWidth);
        memberState.setTextSize(toolTextSize);

        if (room != null) {
            room.setMemberState(memberState);
        }


        showLog("设置铅笔");
    }


}
