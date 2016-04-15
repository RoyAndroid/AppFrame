/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.appframe.biz.Widget.XListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appframe.R;
import com.appframe.lib.utils.DensityUtils;

public class XFooterView extends LinearLayout {
    //是否还有下一页数据
    private boolean mHasMore = true;

    private Context mContext;

    private View mContentView;

    private LinearLayout mContainer;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private ImageView mArrowImageView;

    private int mState = STATE_NORMAL;

    private final int ROTATE_ANIM_DURATION = 180;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private Context context;

    public XFooterView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setLoadEnable(boolean b) {
        mHasMore = b;
        mHeight = DensityUtils.dip2px(context, 64);
        if (b) {
            setState(STATE_NORMAL);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mArrowImageView.setVisibility(View.GONE);
            mHintTextView.setText(R.string.footer_load_more_hint_all);
            mState = -1;
        }
    }

    public boolean canLoadMore() {
        return mHasMore;
    }

    /**
     * normal status
     */
    public void normal() {
        mHintTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


    /**
     * loading status
     */
    public void loading() {
        mHintTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = mContext.getResources().getDimensionPixelSize(R.dimen.dp_60);
        mContentView.setLayoutParams(lp);
    }


    private void initView(Context context) {
        this.context = context;
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.xlistview_footer, null);
        addView(mContainer, lp);
        setGravity(Gravity.TOP);

        mHintTextView = (TextView) findViewById(R.id.xlistview_footer_hint_textview);
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_footer_progressbar);

        mContext = context;

        mContentView = mContainer.findViewById(R.id.xlistview_footer_content);

        mArrowImageView = (ImageView) findViewById(R.id.xlistview_footer_arrow);

        mRotateUpAnim = new RotateAnimation(180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

    }

    public void setState(int state) {
        if (!mHasMore || state == mState) return;

        if (state == STATE_LOADING) {    // 显示进度
            mProgressBar.setVisibility(View.VISIBLE);
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    mArrowImageView.startAnimation(mRotateUpAnim);
                }
                if (mState == STATE_LOADING) {
                    mArrowImageView.clearAnimation();
                }
                mHintTextView.setText(R.string.footer_load_more_hint_normal);
                break;
            case STATE_READY:
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateDownAnim);
                    mHintTextView.setText(R.string.footer_load_more_hint_ready);
                }
                break;
            case STATE_LOADING:
                mHintTextView.setText(R.string.footer_load_more_hint_loading_next);
                break;
            default:
        }

        mState = state;
    }

    public void setVisibleHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

    int mHeight = 0;

}
