package com.leo.robot.ui.wire_stripping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo.robot.R;

import java.util.List;

/**
 * created by Leo on 2019/4/25 22 : 09
 */


public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;

    public ActionAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_action, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvAction.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView mTvAction;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvAction = (TextView) itemView.findViewById(R.id.tv_action);
        }
    }
}
