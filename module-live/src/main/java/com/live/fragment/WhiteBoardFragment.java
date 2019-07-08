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
import com.herewhite.sdk.domain.MemberState;
import com.lib.fastkit.utils.color.ColorUtil;
import com.lib.ui.fragment.BaseAppFragment;
import com.live.R;
import com.live.R2;

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

        setTool(room);
        refreshRoom();
    }


    public void setTool(Room room) {

        MemberState memberState = new MemberState();
        memberState.setStrokeColor(ColorUtil.int2Rgb(getResources().getColor(R.color.base_money)));
        memberState.setCurrentApplianceName(Appliance.PENCIL);
        memberState.setStrokeWidth(5);
        memberState.setTextSize(5);

        if (room != null) {
            room.setMemberState(memberState);
        }

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


}
