package com.appframe.biz.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appframe.R;

/**
 * 通用标题加列表dialog
 * Created by Roy
 * Date: 15/9/9
 */
public class ActionListDialog extends Dialog {
    private ListView mListView;
    private ActionListAdapter mAdapter;
    private TextView mTitleTv;
    private Context mContext;

    public ActionListDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        setContentView(R.layout.dialog_action_list);

        mAdapter = new ActionListAdapter(context, theme);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);

        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mTitleTv.setVisibility(View.GONE);
    }

    /**
     * 设置Dialgo的标题，默认不显示
     * @param title
     * @return
     */
    public ActionListDialog addTitle(String title) {
        if (title != null && title.length() > 0) {
            mTitleTv.setText(title);
            mTitleTv.setVisibility(View.VISIBLE);
        } else {
            mTitleTv.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 添加listView的每一项及监听事件
     * @param title
     * @param listener
     * @return
     */
    public ActionListDialog addItem(String title, OnClickListener listener) {
        ActionItem item = new ActionItem(title, listener);
        mAdapter.add(item);
        return this;
    }

    public interface OnClickListener {
        void didClick(ActionListDialog dialog, String itemTitle);
    }

    public static class ActionItem {
        private String title;
        private OnClickListener listener;

        private ActionItem(String title, OnClickListener listener) {
            this.title = title;
            this.listener = listener;
        }
    }

    class ActionListAdapter extends ArrayAdapter<ActionItem> {
        LayoutInflater inflater;

        ActionListAdapter(Context context, int resource) {
            super(context, resource);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.dialog_action_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ActionItem item = getItem(position);
            holder.textView.setText(item.title);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionItem item = getItem(position);
                    if (item.listener != null)
                        item.listener.didClick(ActionListDialog.this, item.title);
                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        TextView textView;
        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.tv_title);
        }
    }

    public boolean showDialog() {
        if (isShowing() || ((Activity) mContext).isFinishing()) {
            return false;
        }
        show();
        return true;
    }

    public boolean dismissDialog() {
        if (isShowing()) {
            try {
                dismiss();
            } catch (Exception e) {
            }
            return true;
        }
        return false;
    }
}
