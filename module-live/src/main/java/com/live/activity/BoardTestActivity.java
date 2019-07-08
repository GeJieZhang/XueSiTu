package com.live.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.herewhite.sdk.Room;
import com.herewhite.sdk.WhiteBroadView;
import com.lib.app.ARouterPathUtils;
import com.lib.app.FragmentTag;
import com.lib.fastkit.utils.fragment_deal.FragmentCustomUtils;
import com.live.R;
import com.live.R2;
import com.live.fragment.WhiteBoardFragment;
import com.live.utils.WhiteBoardUtils;

import butterknife.BindView;


@Route(path = ARouterPathUtils.Live_BoardTestActivity)
public class BoardTestActivity extends BaseRoomActivity {


    //    @BindView(R2.id.bord)
//    WhiteBroadView bord;
    @BindView(R2.id.f_board)
    FrameLayout fBoard;

//    @Override
//    protected void onCreateView() {
//
//        initWhiteBorad();
////        WhiteBroadView whiteBroadView = new WhiteBroadView(this);
////
////
////        WhiteBoardUtils.getInstance().joinToRoom(this, whiteBroadView);
////        fBoard.addView(whiteBroadView);
//
//
//        WhiteBoardFragment whiteBoardFragment = new WhiteBoardFragment(whiteBroadView, whiteBoardRoom);
//        FragmentCustomUtils.setFragment(this, R.id.f_board, whiteBoardFragment, FragmentTag.WhiteBoardFragment);
//
//
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_borad);
        initWhiteBorad();
        WhiteBoardFragment whiteBoardFragment = new WhiteBoardFragment(whiteBroadView, whiteBoardRoom);
        FragmentCustomUtils.setFragment(this, R.id.f_board, whiteBoardFragment, FragmentTag.WhiteBoardFragment);


    }

    WhiteBroadView whiteBroadView;

    private Room whiteBoardRoom;

    /**
     * 初始化白板
     */
    private void initWhiteBorad() {

        whiteBroadView = new WhiteBroadView(this);
        WhiteBoardUtils.getInstance().joinToRoom(this, whiteBroadView);


    }

//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_borad;
//    }


}
