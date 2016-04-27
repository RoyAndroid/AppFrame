package com.appframe.biz.Widget.ToolBar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appframe.R;

/**
 * Created by Roy
 * Date: 16/3/10
 */
public class ToolbarHelper<T> {
    private Context mContext;

    FrameLayout mContentView;

    android.support.v7.widget.Toolbar mToolbar;

    View mUserView;

    ImageView mBottomLine;

    int mToolbarHeight = 0;

    public ToolbarHelper(Context context, int layoutResId) {
        this.mContext = context;
        mToolbarHeight = mContext.getResources().getDimensionPixelSize(R.dimen.dp_50);
        buildContentView();
        createUserView(layoutResId);
        addTitleBottomLine();
    }

    private void buildContentView() {
        mContentView = new FrameLayout(mContext);
        mContentView.setBackgroundResource(R.color.white);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    public void createBasebar() {
        mToolbar = new Toolbar(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                mToolbarHeight);
        mContentView.addView(mToolbar, params);
    }

    public void createSearchbar() {
        mToolbar = new SearchBar(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                mToolbarHeight);
        mContentView.addView(mToolbar, params);
    }

    private void createUserView(int layoutResId) {
        mUserView = LayoutInflater.from(mContext).inflate(layoutResId, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        params.topMargin = mToolbarHeight;
        mContentView.addView(mUserView, params);
    }

    private void addTitleBottomLine() {
        mBottomLine = new ImageView(mContext);
        mBottomLine.setBackgroundResource(R.drawable.shade_top_bar);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                mContext.getResources().getDimensionPixelSize(R.dimen.dp_2));
        params.topMargin = mToolbarHeight;
        mContentView.addView(mBottomLine, params);
    }

    public void setTitleBottomLineHeight(int height) {
        if (mBottomLine != null) {
            mBottomLine.getLayoutParams().height = height;
        }
    }

    public void setTitleBottomLineColor(int color) {
        if (mBottomLine != null) {
            mBottomLine.setBackgroundColor(color);
        }
    }

    public View getContentVew() {
        return mContentView;
    }

    public android.support.v7.widget.Toolbar getToolbar() {
        return mToolbar;
    }

    public final void resetTitleTop(int top) {
        addStatusBarBg(top);
        ((FrameLayout.LayoutParams) mToolbar.getLayoutParams()).topMargin = top;
        FrameLayout.LayoutParams bottomlineLp = (FrameLayout.LayoutParams) mBottomLine.getLayoutParams();
        bottomlineLp.topMargin = bottomlineLp.topMargin + top;
        FrameLayout.LayoutParams userViewLp = (FrameLayout.LayoutParams) mUserView.getLayoutParams();
        userViewLp.topMargin = userViewLp.topMargin + top;
    }

    private void addStatusBarBg(int height) {
        ImageView mBottomLine = new ImageView(mContext);
        mBottomLine.setBackgroundResource(R.color.black);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                height);
        mContentView.addView(mBottomLine, 0, params);
    }

}

