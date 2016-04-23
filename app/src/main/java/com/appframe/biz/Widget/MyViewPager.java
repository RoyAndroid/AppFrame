package com.appframe.biz.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Roy
 * Date: 15/11/13
 */
public class MyViewPager extends android.support.v4.view.ViewPager {

    private boolean canTouch = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canTouch) {
            try {
                return super.onTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canTouch) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        } else {
            return false;
        }
    }

    /* 禁止手势滑动 */
    public void setTouchEnable(boolean canTouch) {
        this.canTouch = canTouch;
    }
}
