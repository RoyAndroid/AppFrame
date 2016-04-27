package com.appframe.biz.Widget.ToolBar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.appframe.R;

/**
 * Created by Roy
 * Date: 16/4/22
 */
public class SearchBar extends android.support.v7.widget.Toolbar {

    ImageView mBtnLeft;
    Button mBtnRight;
    EditText edSearch;

    public SearchBar(Context context) {
        this(context, null, 0);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.base_search_layout, this);

        initView();
    }

    private void initView() {
        mBtnLeft = (ImageView) findViewById(R.id.btn_left);
        mBtnRight = (Button) findViewById(R.id.btn_right);
        edSearch = (EditText) findViewById(R.id.ed_search);
    }

    public String getSearchText() {
        ensureInit();
        return edSearch.getText().toString().trim();
    }

    public void setSearchText(String text) {
        ensureInit();
        edSearch.setText(text);
    }

    public void addSearchText(String text) {
        ensureInit();
        edSearch.setText(edSearch.getText().toString().trim() + "  " + text);
    }

    private void ensureInit() {
        if (mBtnLeft == null)
            mBtnLeft = (ImageView) findViewById(R.id.btn_left);
        if (mBtnRight == null)
            mBtnRight = (Button) findViewById(R.id.btn_right);
        if (edSearch == null)
            edSearch = (EditText) findViewById(R.id.ed_search);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mBtnLeft.setOnClickListener(l);
        mBtnRight.setOnClickListener(l);
        edSearch.setOnClickListener(l);
        super.setOnClickListener(l);
    }
}
