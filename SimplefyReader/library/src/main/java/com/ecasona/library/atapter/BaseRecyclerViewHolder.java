package com.ecasona.library.atapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aiy on 2016/9/12.
 * <p>
 * des:
 */

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    public abstract void initView(View itemView);
}
