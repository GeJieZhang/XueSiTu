package com.share.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lib.fastkit.views.circle_image.niv.NiceImageView;
import com.lib.utls.glide.GlideConfig;
import com.share.R;
import com.share.R2;
import com.share.utils.CommentsPopupUtils;

import butterknife.BindView;

/**
 * 自定义分享圈得item
 * 因为分享圈得tem种类较多
 */
public class CoustomShareView extends LinearLayout implements View.OnClickListener {

    //中间显示问答
    public static final int TYPE_ASK = 0;
    //分享老师
    public static final int TYPE_CONTENT_1 = 1;
    //话题
    public static final int TYPE_CONTENT_2 = 2;
    //课程
    public static final int TYPE_CONTENT_3 = 3;

    //新闻
    public static final int TYPE_NEWS = 4;

    //加载得根布局
    private View root;

    private Context context;


    private LinearLayout lin_parent;

    /**
     * 头部
     *
     * @param context
     */

    private ImageView iv_head;
    private TextView tv_title;
    private ImageView iv_lock;
    private TextView tv_subject;

    /**
     * 答疑
     *
     * @param context
     */
    private LinearLayout lin_content_ask;
    private ImageView iv_image1;
    private ImageView iv_image2;
    private TextView tv_money;

    /**
     * 内容1
     *
     * @param context
     */

    private TextView tv_content_text1;
    private TextView tv_content_text2;


    /**
     * 新闻
     *
     * @param context
     */


    private LinearLayout lin_news;
    private ImageView iv_newImage;
    private TextView tv_news_content;
    private TextView tv_tip;
    private TextView tv_time;

    /**
     * 底部
     *
     * @param context
     */

    private LinearLayout lin_zan;
    private LinearLayout lin_comments;
    private ImageView iv_zan;
    private TextView tv_zan;
    private ImageView iv_comments;
    private TextView tv_comments;


    public CoustomShareView(Context context) {
        this(context, null);
    }

    public CoustomShareView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoustomShareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        initView();
    }

    private void initView() {


        root = LayoutInflater.from(context).inflate(R.layout.item_base_share, this);

        lin_parent = root.findViewById(R.id.lin_parent);
        lin_parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onItemClick();

                }

            }
        });
        /**
         * 头部
         *
         * @param context
         */

        iv_head = root.findViewById(R.id.iv_head);
        tv_title = root.findViewById(R.id.tv_title);
        iv_lock = root.findViewById(R.id.iv_lock);
        tv_subject = root.findViewById(R.id.tv_subject);

        /**
         * 答疑
         *
         * @param context
         */
        lin_content_ask = root.findViewById(R.id.lin_content_ask);
        iv_image1 = root.findViewById(R.id.iv_image1);
        iv_image2 = root.findViewById(R.id.iv_image2);
        tv_money = root.findViewById(R.id.tv_money);

        /**
         * 内容1
         *
         * @param context
         */

        tv_content_text1 = root.findViewById(R.id.tv_content_text1);
        tv_content_text2 = root.findViewById(R.id.tv_content_text2);


        /**
         * 新闻
         *
         * @param context
         */

        lin_news = root.findViewById(R.id.lin_news);
        iv_newImage = root.findViewById(R.id.iv_newImage);
        tv_news_content = root.findViewById(R.id.tv_news_content);
        tv_tip = root.findViewById(R.id.tv_tip);
        tv_time = root.findViewById(R.id.tv_time);

        /**
         * 底部
         *
         * @param context
         */
        lin_zan = root.findViewById(R.id.lin_zan);
        lin_zan.setOnClickListener(this);
        lin_comments = root.findViewById(R.id.lin_comments);
        lin_comments.setOnClickListener(this);
        iv_zan = root.findViewById(R.id.iv_zan);
        tv_zan = root.findViewById(R.id.tv_zan);
        iv_comments = root.findViewById(R.id.iv_comments);
        tv_comments = root.findViewById(R.id.tv_comments);


    }


    /**
     * 通过类型来控制显示与隐藏布局
     *
     * @param type
     */
    public void setViewType(int type) {

        switch (type) {
            case TYPE_ASK: {
                //问答
                tv_subject.setVisibility(GONE);
                tv_content_text1.setVisibility(GONE);
                tv_content_text2.setVisibility(GONE);
                lin_news.setVisibility(GONE);


                break;
            }
            case TYPE_CONTENT_1: {
                //分享老师
                lin_content_ask.setVisibility(GONE);
                iv_lock.setVisibility(GONE);
                tv_subject.setVisibility(GONE);
                tv_content_text2.setVisibility(GONE);
                lin_news.setVisibility(GONE);

                break;
            }
            case TYPE_CONTENT_2: {
                //话题
                lin_content_ask.setVisibility(GONE);
                iv_lock.setVisibility(GONE);
                tv_subject.setVisibility(GONE);
                lin_news.setVisibility(GONE);

                break;
            }
            case TYPE_CONTENT_3: {

                //课程
                lin_content_ask.setVisibility(GONE);
                iv_lock.setVisibility(GONE);
                tv_subject.setVisibility(VISIBLE);
                tv_content_text2.setVisibility(GONE);
                lin_news.setVisibility(GONE);
                break;
            }
            case TYPE_NEWS: {
                lin_content_ask.setVisibility(GONE);
                iv_lock.setVisibility(GONE);
                tv_subject.setVisibility(GONE);
                tv_content_text1.setVisibility(GONE);
                tv_content_text2.setVisibility(GONE);

                break;
            }

        }


    }


    /**
     * 设置头像
     *
     * @param url
     */
    public void setIv_head(String url) {

        Glide.with(context)
                .load(url)
                .apply(GlideConfig.getRectangleOptions())
                .into(iv_head);

    }


    /**
     * 设置标题
     *
     * @param str
     */
    public void setTv_title(String str) {
        tv_title.setText(str);

    }


    /**
     * 设置锁是否打开
     *
     * @param b
     */
    public void setIv_lock_Open(boolean b) {

        if (b) {

            iv_lock.setImageResource(R.mipmap.icon_unlock);

        } else {
            iv_lock.setImageResource(R.mipmap.icon_lock);
        }

    }

    /**
     * 设置学科
     *
     * @param str
     */
    public void setTv_subject(String str) {

        tv_subject.setText(str);

    }

    /**
     * 设置问答的第一张图片
     */
    public void setIv_image1(String url) {
        Glide.with(context)
                .load(url)
                .apply(GlideConfig.getRectangleOptions())
                .into(iv_image1);
    }

    /**
     * 设置问答的第二张图片
     */
    public void setIv_image2(String url) {
        Glide.with(context)
                .load(url)
                .apply(GlideConfig.getRectangleOptions())
                .into(iv_image2);
    }

    /**
     * 设置兔币
     */
    public void setTv_money(String money) {
        tv_money.setText(money);


    }

    /**
     * 设置内容1
     *
     * @param str
     */
    public void setTv_content_text1(String str) {

        tv_content_text1.setText(str);

    }

    /**
     * 设置内容2
     *
     * @param str
     */
    public void setTv_content_text2(String str) {

        tv_content_text2.setText(str);

    }

    /**
     * 设置新闻的图片
     *
     * @param url
     */
    public void setIv_newImage(String url) {
        Glide.with(context)
                .load(url)
                .apply(GlideConfig.getRectangleOptions())
                .into(iv_newImage);
    }


    /**
     * 设置新闻内容
     *
     * @param str
     */
    public void setTv_news_content(String str) {

        tv_news_content.setText(str);

    }


    /**
     * 是否显示兔讯红字
     *
     * @param b
     */
    public void setTv_tip_Show(boolean b) {

        if (b) {

            tv_tip.setVisibility(VISIBLE);

        } else {
            tv_tip.setVisibility(GONE);
        }

    }


    public void setTv_Time(String str) {

        tv_time.setText(str);
    }

    /**
     * 设置赞的数量
     *
     * @param str
     */
    public void setTv_zan(String str) {

        tv_zan.setText(str);

    }


    /**
     * 设置评论数量
     *
     * @param str
     */
    public void setTv_comments(String str) {

        tv_comments.setText(str);

    }


    /**
     * 设置红心的状态
     *
     * @param b
     */
    public void setIv_zan_Show(boolean b) {

        if (b) {
            iv_zan.setImageResource(R.mipmap.icon_love_selected);
        } else {
            iv_zan.setImageResource(R.mipmap.icon_love_default);
        }


    }


    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.lin_zan) {

            if (listener != null) {
                listener.onZanClick();

            }
        } else if (i == R.id.lin_comments) {
            if (listener != null) {
                listener.onCommentsClick();

            }
        }
    }


    private CoustomShareViewListener listener;

    public void setCoustomShareViewListener(CoustomShareViewListener coustomShareViewListener) {

        this.listener = coustomShareViewListener;

    }

    public interface CoustomShareViewListener {

        void onCommentsClick();


        void onZanClick();

        void onItemClick();

    }


}
