package com.ecasona.library.atapter;

import java.util.ArrayList;

/**
 * A adapter using View Holder to display the item of a list view;
 *
 * @param <T>
 */
public class ListViewDataAdapter<T> extends ListViewDataAdapterBase<T> {

    protected ArrayList<T> mItemDataList = new ArrayList<>();

    public ListViewDataAdapter() {

    }

    /**
     * @param viewHolderCreator The view holder creator will create a View Holder that extends {@link ViewHolderBase}
     */
    public ListViewDataAdapter(ViewHolderCreator<T> viewHolderCreator) {
        super(viewHolderCreator);
    }

    public ArrayList<T> getDataList() {
        return mItemDataList;
    }

    @Override
    public int getCount() {
        return mItemDataList.size();
    }

    @Override
    public T getItem(int position) {
        if (mItemDataList.size() <= position || position < 0) {
            return null;
        }
        return mItemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
