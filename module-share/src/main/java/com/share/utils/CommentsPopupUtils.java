package com.share.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.fastkit.views.load_state_view.MultiStateView;
import com.lib.fastkit.views.recyclerview.zhanghongyang.base.ViewHolder;
import com.lib.fastkit.views.spring_refresh.container.DefaultFooter;
import com.lib.fastkit.views.spring_refresh.container.DefaultHeader;
import com.lib.fastkit.views.spring_refresh.widget.SpringView;
import com.lib.http.call_back.HttpDialogCallBack;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.ui.adapter.BaseAdapter;
import com.lib.utls.glide.GlideConfig;
import com.share.bean.CommentBean;
import com.share.bean.ToCommentBean;
import com.share.bean.ZanBean;
import com.zyyoona7.popup.EasyPopup;
import com.share.R;

import java.util.ArrayList;
import java.util.List;

public class CommentsPopupUtils implements View.OnClickListener {


    private Activity activity;


    /**
     * 初始化控件
     *
     * @param ac
     */

    private View v_back;
    private TextView tv_comments_num;

    private ImageView iv_delete;


    private MultiStateView state_view;
    private SpringView springView;
    private RecyclerView rv;

    private EditText et_message;
    private ImageView iv_delete_message;
    private Button btn_send;


    private HomeAdapter homeAdapter;

    public CommentsPopupUtils(Activity ac) {
        this.activity = ac;

        initVoicePopu();


    }


    //------------------------------------------------------------------------------------------popup

    private EasyPopup voicePopu;


    private int share_circle_id = 0;

    public void showVoicePopu(View view, int id) {
        page = 0;
        this.share_circle_id = id;
        voicePopu.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        requestCommentList();

    }


    public void dismiss() {
        voicePopu.dismiss();
    }


    private void initVoicePopu() {
        voicePopu = EasyPopup.create()
                .setContext(activity)
                .setContentView(R.layout.popup_comments)
                .setWidth(WindowManager.LayoutParams.FILL_PARENT)
                .setHeight(WindowManager.LayoutParams.FILL_PARENT)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup basePopup) {


                        initMyView(view);


                    }


                })
                .setFocusAndOutsideEnable(true)
                .apply();

    }


    private List<CommentBean.ObjBean.RowsBean> list = new ArrayList<>();

    private void initMyView(View view) {

        v_back = view.findViewById(R.id.v_back);
        v_back.setOnClickListener(this);
        tv_comments_num = view.findViewById(R.id.tv_comments_num);
        iv_delete = view.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        state_view = view.findViewById(R.id.state_view);
        state_view.setViewState(MultiStateView.VIEW_STATE_LOADING);
        state_view.setMultiStateViewLisener(new MultiStateView.MultiStateViewLisener() {
            @Override
            public void onTryAgain() {
                requestCommentList();
            }
        });
        springView = view.findViewById(R.id.springView);
        rv = view.findViewById(R.id.rv);
        et_message = view.findViewById(R.id.et_message);
        iv_delete_message = view.findViewById(R.id.iv_delete_message);
        iv_delete_message.setOnClickListener(this);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        homeAdapter = new HomeAdapter(activity, list);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        rv.setAdapter(homeAdapter);


        springView.setHeader(new DefaultHeader(activity));
        springView.setFooter(new DefaultFooter(activity));

        springView.setEnableHeader(false);
        springView.setEnableFooter(true);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {


                page = 0;
                requestCommentList();

            }

            @Override
            public void onLoadmore() {
                page++;
                requestCommentList();
            }
        });


    }

    private int page = 0;

    private void requestCommentList() {

        HttpUtils.with(activity)
                .addParam("requestType", "SHARE_COMMENT_LIST")
                .addParam("page", page)
                .addParam("limit", 10)
                .addParam("share_circle_id", share_circle_id)
                .addParam("token", SharedPreferenceManager.getInstance(activity).getUserCache().getUserToken())
                .execute(new HttpNormalCallBack<CommentBean>() {
                    @Override
                    public void onSuccess(CommentBean result) {
                        springView.onFinishFreshAndLoad();


                        if (result.getCode() == CodeUtil.CODE_200) {
                            tv_comments_num.setText(result.getObj().getTotal());
                            if (page == 0) {
                                list.clear();
                            }
                            list.addAll(result.getObj().getRows());
                            homeAdapter.notifyDataSetChanged();
                            state_view.setViewState(MultiStateView.VIEW_STATE_CONTENT);

                        } else {
                            state_view.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }

                    }

                    @Override
                    public void onError(String e) {
                        state_view.setViewState(MultiStateView.VIEW_STATE_NETWORK_ERROR);
                    }
                });

    }


    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.v_back) {
            dismiss();

        } else if (i == R.id.iv_delete) {
            dismiss();
        } else if (i == R.id.btn_send) {

            requestSendComment();


        } else if (i == R.id.iv_delete_message) {
            et_message.setText("");


        }


    }


    public interface CommentsPopupUtilsListener {

        void onCommetntCountChange(int count);

    }

    private CommentsPopupUtilsListener listener;


    public void setCommentsPopupUtilsListenerr(CommentsPopupUtilsListener CommentsPopupUtilsListener) {

        this.listener = CommentsPopupUtilsListener;

    }

    private class HomeAdapter extends BaseAdapter<CommentBean.ObjBean.RowsBean> {

        public HomeAdapter(Context context, List<CommentBean.ObjBean.RowsBean> mData) {
            super(context, mData);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_comment;
        }

        @Override
        protected void toBindViewHolder(ViewHolder holder, int position, List<CommentBean.ObjBean.RowsBean> mData) {


            final CommentBean.ObjBean.RowsBean rowsBean = mData.get(position);
            String url = rowsBean.getUser_icon();
            ImageView iv_head = holder.getView(R.id.iv_head);
            Glide.with(activity)
                    .load(url)
                    .apply(GlideConfig.getRectangleOptions())
                    .into(iv_head);


            holder.setText(R.id.tv_title, rowsBean.getUser_name());
            holder.setText(R.id.tv_content_text2, rowsBean.getContent());


            int likeState = rowsBean.getLike_status();
            final ImageView iv_zan = holder.getView(R.id.iv_zan);
            if (likeState == 1) {
                iv_zan.setImageResource(R.mipmap.icon_love_selected);
            } else {
                iv_zan.setImageResource(R.mipmap.icon_love_default);
            }


            final TextView tv_zan = holder.getView(R.id.tv_zan);

            tv_zan.setText(rowsBean.getLike_count() + "");


            holder.getView(R.id.lin_zan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id = rowsBean.getComment_id();
                    requestZan(id, iv_zan, tv_zan);
                }
            });
        }
    }

    /**
     * 点赞
     *
     * @param id
     * @param
     */
    private void requestZan(int id, final ImageView iv_zan, final TextView tv_zan) {


        HttpUtils.with(activity)
                .addParam("requestType", "SHARE_COMMENT_LIKE")
                .addParam("token", SharedPreferenceManager.getInstance(activity).getUserCache().getUserToken())
                .addParam("comment_id", id)
                .execute(new HttpDialogCallBack<ZanBean>() {


                    @Override
                    public void onSuccess(ZanBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {
                            int zan = result.getObj().getLike_status();

                            if (zan == 1) {
                                iv_zan.setImageResource(R.mipmap.icon_love_selected);
                            } else {
                                iv_zan.setImageResource(R.mipmap.icon_love_default);
                            }

                            tv_zan.setText(result.getObj().getLike_count() + "");

                        }

                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });

    }


    /**
     * 发送评论
     */
    private void requestSendComment() {

        String comment_content = et_message.getText().toString().trim();
        if (comment_content.equals("")) {

            showToast("内容不能为空!");
            return;
        }

        HttpUtils.with(activity)
                .addParam("requestType", "SHARE_COMMENT")
                .addParam("token", SharedPreferenceManager.getInstance(activity).getUserCache().getUserToken())
                .addParam("share_circle_id", share_circle_id)
                .addParam("comment_content", comment_content)
                .execute(new HttpDialogCallBack<ToCommentBean>() {


                    @Override
                    public void onSuccess(ToCommentBean result) {

                        if (result.getCode() == CodeUtil.CODE_200) {

                            ToCommentBean.ObjBean objBean = result.getObj();
                            CommentBean.ObjBean.RowsBean rowsBean = new CommentBean.ObjBean.RowsBean();
                            rowsBean.setComment_id(objBean.getComment_id());
                            rowsBean.setContent(objBean.getContent());
                            rowsBean.setCreate_date(objBean.getCreate_date());
                            rowsBean.setLike_count(objBean.getLike_count());
                            rowsBean.setLike_status(objBean.getLike_status());
                            rowsBean.setUser_name(objBean.getUser_name());
                            rowsBean.setUser_icon(objBean.getUser_icon());
                            list.add(rowsBean);
                            homeAdapter.notifyDataSetChanged();
                            et_message.setText("");
                            tv_comments_num.setText(objBean.getComment_count() + "");

                            if (listener != null) {

                                listener.onCommetntCountChange(objBean.getComment_count());
                            }


                        }

                        showToast(result.getMsg());

                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }

    /**
     * Toast
     *
     * @param text
     */
    private Toast mToast;

    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(activity, text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }
}
