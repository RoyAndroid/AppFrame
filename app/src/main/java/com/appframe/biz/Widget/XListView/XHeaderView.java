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
import com.appframe.lib.Log.L;
import com.appframe.lib.utils.DensityUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XHeaderView extends LinearLayout  {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private TextView mHeaderTimeView;
    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private final int ROTATE_ANIM_DURATION = 180;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    private long mLastRefresh = System.currentTimeMillis();

    private Context context;

    public XHeaderView(Context context) {
        super(context);
        initView(context);
    }

    public XHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.xlistview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        mHeaderTimeView = (TextView) findViewById(R.id.xlistview_header_time);
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    public void setState(int state) {
        if (state == mState) return;

        if (state == STATE_REFRESHING) {    // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mHintTextView.setText(R.string.header_hint_refresh_normal);
                break;
            case STATE_READY:
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mHintTextView.setText(R.string.header_hint_refresh_ready);
                }
                break;
            case STATE_REFRESHING:
                mHintTextView.setText(R.string.header_hint_refresh_loading);
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
    boolean mEnablePullRefresh = false;

    public void setPullToRefresh(boolean ifFresh) {
        mEnablePullRefresh = ifFresh;
        if (ifFresh) {
            mHeight = DensityUtils.dip2px(context, 64);
        } else {
            mHeight = 0;
        }
    }

    public boolean canRefresh(){
        return mEnablePullRefresh;
    }

    /** 处理时间显示UI **/
    public void setRefreshTime(long time) {
        mLastRefresh = time;
        mHeaderTimeView.setText("刚刚");
    }

    public long getRefreshTime(){
        return mLastRefresh;
    }

    boolean mHasSetLastRefreshTime = false;

    public void setRefreshText() {
        if (!mHasSetLastRefreshTime) {
            mHasSetLastRefreshTime = true;
            mHeaderTimeView.setText(showTime(mLastRefresh));
        }
    }

    public void resetRefreshText() {
        mHasSetLastRefreshTime = false;
    }

    private static long min1 = 60 * 1000;
    private static long h1 = 60 * min1;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfShowYear = new SimpleDateFormat("yyyy年MM月dd日");
    private SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private SimpleDateFormat sdfMonth = new SimpleDateFormat("MM月dd日");

    private String showTime(long time) {
        if (time == 0) {
            return "刚刚";
        }

        Date date = new Date(mLastRefresh);
        String timeStr;
        long timelong = new Date().getTime() - date.getTime();
        L.d("ChatManager", "timelong is:" + timelong);
        if (timelong > h1) {
            if (timelong / h1 > 24) {
                if (sdfYear.format(date).equals(sdfYear.format(new Date()))) {
                    timeStr = sdfMonth.format(date);
                } else {
                    timeStr = sdfShowYear.format(date);
                }
            } else {
                int timeInt = (int) (timelong / h1);
                timeInt = timeInt == 0 ? 1 : timeInt;
                timeStr = timeInt + "小时前";
            }
        } else if (timelong / min1 >= 1) {
            int timeInt = (int) (timelong / min1);
            timeInt = timeInt == 0 ? 1 : timeInt;
            timeStr = timeInt + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        return timeStr;
    }

}
