package com.lib.fastkit.views.load_state_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


import com.lib.fastkit.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * View that contains 4 different states: Content, Error, Empty, and Loading.<br>
 * Each state has their own separate layout which can be shown/hidden by setting
 * the {@link ViewState} accordingly
 * Every MultiStateView <b><i>MUST</i></b> contain a content view. The content view
 * is obtained from whatever is inside of the tags of the view via its XML declaration
 */
public class MultiStateView extends FrameLayout {
    public static final int VIEW_STATE_CONTENT = 0;
    public static final int VIEW_STATE_ERROR = 1;
    public static final int VIEW_STATE_NETWORK_ERROR = 2;
    public static final int VIEW_STATE_EMPTY = 3;
    public static final int VIEW_STATE_LOADING = 4;
    private int mLoadingViewResId = -1;
    private int mEmptyViewResId = -1;
    private int mErrorViewResId = -1;
    private int mNetworkErrorViewResId = -1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VIEW_STATE_CONTENT, VIEW_STATE_ERROR, VIEW_STATE_NETWORK_ERROR, VIEW_STATE_EMPTY, VIEW_STATE_LOADING})
    public @interface ViewState {
    }

    private LayoutInflater mInflater;
    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mNetworkErrorView;
    @ViewState
    private int mViewState = VIEW_STATE_CONTENT;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Kit_MultiStateView);
        mLoadingViewResId = a.getResourceId(R.styleable.Kit_MultiStateView_loadingView, -1);
        mEmptyViewResId = a.getResourceId(R.styleable.Kit_MultiStateView_emptyView, -1);
        mErrorViewResId = a.getResourceId(R.styleable.Kit_MultiStateView_errorView, -1);
        mNetworkErrorViewResId = a.getResourceId(R.styleable.Kit_MultiStateView_networkErrorView, -1);
        int viewState = a.getInt(R.styleable.Kit_MultiStateView_viewState, VIEW_STATE_CONTENT);
        switch (viewState) {
            case VIEW_STATE_CONTENT:
                mViewState = VIEW_STATE_CONTENT;
                break;
            case VIEW_STATE_ERROR:
                mViewState = VIEW_STATE_ERROR;
                break;
            case VIEW_STATE_NETWORK_ERROR:
                mViewState = VIEW_STATE_NETWORK_ERROR;
                break;
            case VIEW_STATE_EMPTY:
                mViewState = VIEW_STATE_EMPTY;
                break;
            case VIEW_STATE_LOADING:
                mViewState = VIEW_STATE_LOADING;
                break;
        }
        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null) throw new IllegalArgumentException("Content view is not defined");
        setView();
    }

    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     * @return
     */
    private boolean isValidContentView(View view) {
        return (mContentView == null || mContentView == view) && view != mLoadingView && view != mErrorView && view != mEmptyView && view != mNetworkErrorView;
    }

    /* All of the addView methods have been overridden so that it can obtain the content view via XML
     It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     any of the setViewForState methods to set views for their given ViewState accordingly */
    @Override
    public void addView(View child) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)) mContentView = child;
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Nullable
    public View getView(@ViewState int state) {
        switch (state) {
            case VIEW_STATE_LOADING:
                return mLoadingView;
            case VIEW_STATE_CONTENT:
                return mContentView;
            case VIEW_STATE_EMPTY:
                return mEmptyView;
            case VIEW_STATE_ERROR:
                return mErrorView;
            case VIEW_STATE_NETWORK_ERROR:
                return mNetworkErrorView;
            default:
                return null;
        }
    }

    @ViewState
    public int getViewState() {
        return mViewState;
    }

    public void setViewState(@ViewState int state) {
        if (state != mViewState) {
            mViewState = state;
            setView();
        }
    }

    private void setView() {
        switch (mViewState) {
            case VIEW_STATE_LOADING:
                if (mLoadingView == null) {
                    if (mLoadingViewResId > -1) {
                        mLoadingView = mInflater.inflate(mLoadingViewResId, this, false);
                        addView(mLoadingView, mLoadingView.getLayoutParams());
                    }
                    if (mLoadingView == null)
                        throw new NullPointerException("Loading View");
                }
                mLoadingView.setVisibility(View.VISIBLE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mNetworkErrorView != null) mNetworkErrorView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                break;
            case VIEW_STATE_EMPTY:
                if (mEmptyView == null) {
                    if (mEmptyViewResId > -1) {
                        mEmptyView = mInflater.inflate(mEmptyViewResId, this, false);
                        addView(mEmptyView, mEmptyView.getLayoutParams());
                        setTryAgainButton(mEmptyView);
                    }
                    if (mEmptyView == null)
                        throw new NullPointerException("Empty View");
                }
                mEmptyView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mNetworkErrorView != null) mNetworkErrorView.setVisibility(View.GONE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                break;
            case VIEW_STATE_ERROR:
                if (mErrorView == null) {
                    if (mErrorViewResId > -1) {
                        mErrorView = mInflater.inflate(mErrorViewResId, this, false);
                        addView(mErrorView, mErrorView.getLayoutParams());

                        setTryAgainButton(mErrorView);

                    }
                    if (mErrorView == null)
                        throw new NullPointerException("Error View");
                }
                mErrorView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                if (mNetworkErrorView != null) mNetworkErrorView.setVisibility(View.GONE);
                break;
            case VIEW_STATE_NETWORK_ERROR:
                if (mNetworkErrorView == null) {
                    if (mNetworkErrorViewResId > -1) {
                        mNetworkErrorView = mInflater.inflate(mNetworkErrorViewResId, this, false);
                        addView(mNetworkErrorView, mNetworkErrorView.getLayoutParams());
                        setTryAgainButton(mNetworkErrorView);
                    }
                    if (mNetworkErrorView == null)
                        throw new NullPointerException("Error View");
                }
                mNetworkErrorView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                break;

            case VIEW_STATE_CONTENT:
            default:
                if (mContentView == null) {
                    throw new NullPointerException("Content View");
                }
                mContentView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                if (mNetworkErrorView != null) mNetworkErrorView.setVisibility(View.GONE);
                break;
        }
    }

    private void setTryAgainButton(View root) {
        Button btn_tryAgain = root.findViewById(R.id.btn_tryAgain);

        btn_tryAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewState(VIEW_STATE_LOADING);
                if (lisener != null) {
                    lisener.onTryAgain();
                }

            }
        });
    }


    private MultiStateViewLisener lisener;

    public void setMultiStateViewLisener(MultiStateViewLisener multiStateViewLisener) {

        this.lisener = multiStateViewLisener;

    }

    public interface MultiStateViewLisener {
        void onTryAgain();
    }
}
