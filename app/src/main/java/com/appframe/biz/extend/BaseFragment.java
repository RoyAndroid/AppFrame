package com.appframe.biz.extend;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appframe.R;
import com.appframe.event.netEvent.LoginSuccEvent;
import com.appframe.lib.Log.L;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Roy
 * Date: 16/3/9
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    protected View mView;

    public String getName() {
        return String.format(">>>> HashCode %d, Name %s", hashCode(), ((Object) this).getClass().getSimpleName());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.d(getName());
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d(getName());
        if (mView != null) {
            ViewGroup p = (ViewGroup) mView.getParent();

            if (p != null) {
                p.removeView(mView);
            }

        } else {
            mView = inflater.inflate(getLayoutResourceId(), container, false);
            initial();
            ensureInit();
        }
        return mView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        L.d(getName());
        super.onViewCreated(view, savedInstanceState);
    }

    /* 加载layout xml */
    protected abstract int getLayoutResourceId();

    /* 初始化数据 */
    protected abstract void initial();


    @Override
    public void onResume() {
        L.d(getName());
        super.onResume();
    }

    @Override
    public void onStart() {
        L.d(getName());
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        L.d(getName());
        super.onAttach(activity);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        L.d(getName() + " UserVisibleHint: %s", isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        L.d(getName());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.d(getName());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
    }

    @Override
    public void onStop() {
        L.d(getName());
        super.onStop();
    }

    @Override
    public void onPause() {
        L.d(getName());
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        L.d(getName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        L.d(getName());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        L.d(getName());
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    public boolean checkActivityExit() {
        return (getActivity() == null || getActivity().isFinishing()) ? true : false;
    }

    public View findView() {
        if (null != mView) {
            return mView;
        }
        return null;
    }

    @Subscribe
    public void onEventMainThread(LoginSuccEvent event) {

    }

    /************************* 控制标题 ************************************/
    protected View mTitleView;
    protected TextView tvTitle;
    protected Button btnLeft;
    protected Button btnRight;

    private void ensureInit() {
        mTitleView = findView().findViewById(R.id.title_bar);
        if (mTitleView != null) {
            tvTitle = (TextView) mTitleView.findViewById(R.id.tv_title_bar_title);
            btnLeft = (Button) mTitleView.findViewById(R.id.btn_left);
            btnRight = (Button) mTitleView.findViewById(R.id.btn_right);
            btnLeft.setOnClickListener(this);
            btnRight.setOnClickListener(this);
        }
    }

    protected void setTilte(CharSequence title) {
        ensureInit();
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    protected void setRightButton(int drawableId) {
        ensureInit();
        if (drawableId > -1) {
            Drawable drawable = getResources().getDrawable(drawableId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btnRight.setCompoundDrawables(null, null, drawable, null);
            btnRight.setVisibility(View.VISIBLE);
        } else {
            btnRight.setCompoundDrawables(null, null, null, null);
        }
    }

    protected void setLeftButton(int drawableId) {
        ensureInit();
        if (drawableId > -1) {
            Drawable drawable = getResources().getDrawable(drawableId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btnLeft.setCompoundDrawables(drawable, null, null, null);
            btnLeft.setVisibility(View.VISIBLE);
        } else {
            btnLeft.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:
                leftAction();
                break;
            case R.id.btn_right:
                rightAction();
                break;
        }
    }

    protected void rightAction(){

    }

    protected void leftAction(){

    }
}
