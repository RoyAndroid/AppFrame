package com.appframe.biz.Widget.ToolBar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appframe.R;
import com.appframe.lib.utils.StringUtils;

/**
 * Created by Roy
 * Date: 16/3/10
 */
public class Toolbar extends android.support.v7.widget.Toolbar {

    Button mBtnLeft, mBtnRight;
    TextView mTitleView;

    public Toolbar(Context context) {
        this(context, null, 0);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.base_toolbar_layout, this);

        initView();
    }

    private void initView() {
        mBtnLeft = (Button) findViewById(R.id.btn_left);
        mBtnRight = (Button) findViewById(R.id.btn_right);

        mTitleView = (TextView) findViewById(R.id.tv_title_bar_title);
    }

    /**
     * 设置标题栏
     *
     * @param title
     */
    public final void setTitle(String title) {
        mTitleView.setText(title);
    }

    /**
     * 设置标题栏
     *
     * @param strRes
     */
    public final void setTitle(int strRes) {
        mTitleView.setText(strRes);
    }

    /**
     * 普通的标题栏 只有标题和返回按钮
     *
     * @param title
     */
    public final void baseTitle(CharSequence title) {
        mTitleView.setText(title);
        mBtnLeft.setVisibility(VISIBLE);
    }

    public void setTitleColor(int textColor) {
        ensureInit();
        mTitleView.setTextColor(textColor);
    }

    public void setTitleDrawable(int resId){
        ensureInit();
        if (resId > -1) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTitleView.setCompoundDrawables(null, null, drawable, null);
        } else {
            mTitleView.setCompoundDrawables(null, null, null, null);
        }
    }

    public void setLeftButton(String text){
        ensureInit();
        mBtnLeft.setText(text);
    }

    public void setLeftButton(int resId, String text) {
        ensureInit();
        if (resId > 0) {
            Drawable drawable = getResources().getDrawable(resId);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBtnLeft.setCompoundDrawables(drawable, null, null, null);
            mBtnLeft.setVisibility(View.VISIBLE);
        } else {
            mBtnLeft.setCompoundDrawables(null, null, null, null);
        }
        if (StringUtils.isNotEmpty(text)) {
            mBtnLeft.setText(text);
        }
    }

    public void setLeftButton(OnClickListener listener) {
        ensureInit();
        if (listener != null) {
            mBtnLeft.setOnClickListener(listener);
        }
        mBtnLeft.setVisibility(listener != null ? View.VISIBLE : View.GONE);
    }


    public void setRightButton(int resId) {
        ensureInit();
        if (resId > -1) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBtnRight.setCompoundDrawables(null, null, drawable, null);
            mBtnRight.setVisibility(View.VISIBLE);
        } else {
            mBtnRight.setCompoundDrawables(null, null, null, null);
        }
    }

    public void setRightButton(String text, OnClickListener listener) {
        ensureInit();
        mBtnRight.setText(text);
        mBtnRight.setVisibility(View.VISIBLE);
        mBtnRight.setOnClickListener(listener);
    }

    public void setRightButton(String text) {
        mBtnRight.setText(text);
        mBtnRight.setVisibility(View.VISIBLE);
    }

    public void showLeftButton(boolean show) {
        ensureInit();
        mBtnLeft.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showRightButton(boolean show) {
        ensureInit();
        mBtnRight.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void ensureInit() {
        if (mBtnLeft == null)
            mBtnLeft = (Button) findViewById(R.id.btn_left);
        if (mBtnRight == null)
            mBtnRight = (Button) findViewById(R.id.btn_right);
        if (mTitleView == null)
            mTitleView = (TextView) findViewById(R.id.tv_title_bar_title);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mBtnLeft.setOnClickListener(l);
        mBtnRight.setOnClickListener(l);
        mTitleView.setOnClickListener(l);
        super.setOnClickListener(l);
    }
}

