package com.appframe.biz.extend;

import android.view.View;

import com.appframe.R;
import com.appframe.biz.Widget.ToolBar.Toolbar;
import com.appframe.biz.Widget.ToolBar.ToolbarHelper;

/**
 * Created by Roy
 * Date: 16/3/10
 */
public abstract class CustomToolBarActivity extends BaseActivity implements View.OnClickListener {

    private ToolbarHelper mToolbarHelper;
    private Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResId) {
        mToolbarHelper = new ToolbarHelper(this, layoutResId);
        mToolbarHelper.createBasebar();
        mToolbar = (Toolbar) mToolbarHelper.getToolbar();
        mToolbar.setOnClickListener(this);
        setContentView(mToolbarHelper.getContentVew());
        setSupportActionBar(mToolbar);
    }

    public View getContentView() {
        return mToolbarHelper != null ? mToolbarHelper.getContentVew() : null;
    }

    public View getTitleBar() {
        return this.mToolbar;
    }

    public ToolbarHelper getToolbarHelper() {
        return mToolbarHelper;
    }

    /**
     * 仅仅设置标题
     *
     * @param titleId
     */
    public void setOnlyTitle(int titleId) {
        if (mToolbar != null)
            mToolbar.setTitle(getString(titleId));
    }

    /**
     * 设置标题文字
     * (non-Javadoc)
     *
     * @see android.app.Activity#setTitle(CharSequence)
     */
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mToolbar != null)
            mToolbar.baseTitle(title);
    }

    /**
     * 设置标题文字
     * (non-Javadoc)
     *
     * @see android.app.Activity#setTitle(int)
     */
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mToolbar != null)
            mToolbar.baseTitle(getString(titleId));
    }

    public void setTitleDrawable(int resId) {
        if (mToolbar != null) {
            mToolbar.setTitleDrawable(resId);
        }
    }

    /**
     * 设置文字类型的action 按钮
     *
     * @param text
     */
    public void setRightButton(String text) {
        if (mToolbar != null)
            mToolbar.setRightButton(text);
    }

    /**
     * 设置图标类型的action按钮
     *
     * @param drawableID
     */
    public void setRightButton(int drawableID) {
        if (mToolbar != null)
            mToolbar.setRightButton(drawableID);
    }

    public void hideRightButton() {
        if (mToolbar != null)
            mToolbar.showRightButton(false);
    }

    public void setLeftButton(int resId) {
        setLeftButton(resId, "");
    }

    public void setLeftButton(int resId, String text) {
        if (mToolbar != null) {
            mToolbar.setLeftButton(resId, text);
        }
    }

    public void setLeftButton(String text) {
        if (mToolbar != null) {
            mToolbar.setLeftButton(text);
        }
    }

    public void hideLeftButton() {
        if (mToolbar != null)
            mToolbar.showLeftButton(false);
    }

    /**
     * 设置底线颜色
     *
     * @param bgColor
     */
    public void setButtomLineColor(int bgColor) {
    }

    @Override
    public void dismiss() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (!isTaskRoot())
                finish();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_left:
                leftAction();
                break;
            case R.id.btn_right:
                rightAction();
                break;
            case R.id.tv_title_bar_title:
                titleAction();
                break;
        }
    }

    /* title按钮的点击事件处理 */
    protected void titleAction() {

    }

    /* 左边按钮的点击事件处理 */
    protected void leftAction() {
        dismiss();
    }

    /* 右边按钮的点击事件处理 */
    protected void rightAction() {

    }
}
