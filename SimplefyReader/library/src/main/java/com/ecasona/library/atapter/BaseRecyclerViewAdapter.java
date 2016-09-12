package com.ecasona.library.atapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by aiy on 2016/9/12.
 * <p>
 * des:
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private List<T> list;

    public BaseRecyclerViewAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutItemId(), parent, false)) {
            @Override
            public void initView(View itemView) {
                initilizaItemView(itemView);
            }
        };
    }

    @Override
    public abstract void onBindViewHolder(BaseRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public abstract void initilizaItemView(View view);

    public abstract int getLayoutItemId();
}
