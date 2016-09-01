package com.ecasona.library.atapter;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by aiy on 2016/9/1.
 */

public abstract class ViewHolderBase<T> {
    private int mLastPosition;
    private int mPosition;
    private View currentView;

    public abstract View createView(LayoutInflater layoutInflater);

    public abstract void showData(int position, T t);

    public void setItemData(int position, View view) {
        mLastPosition = mPosition;
        this.mPosition = position;
        this.currentView = view;
    }

    /**
     * Check if the View Holder is still display the same data after back to screen.
     * <p/>
     * A view in a ListView or GridView may go down the screen and then back,
     * <p/>
     * for efficiency, in getView() method, a convertView will be reused.
     * <p/>
     * If the convertView is reused, View Holder will hold new data.
     */
    public boolean stillHoldLastItemData() {
        return mLastPosition == mPosition;
    }
}
