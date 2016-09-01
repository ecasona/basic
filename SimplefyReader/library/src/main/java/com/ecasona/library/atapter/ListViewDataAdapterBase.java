/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ecasona.library.atapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * A adapter using View Holder to display the item of a list view;
 *
 * @param <T>
 */
public abstract class ListViewDataAdapterBase<T> extends BaseAdapter {

    protected ViewHolderCreator<T> mViewHolderCreator;
    protected ViewHolderCreator<T> mLazyCreator;
    protected boolean mForceCreateView = false;

    public ListViewDataAdapterBase() {

    }

    public ListViewDataAdapterBase(final Object enclosingInstance, final Class<?> cls) {
        setViewHolderClass(enclosingInstance, cls);
    }

    /**
     * @param viewHolderCreator The view holder creator will create a View Holder that extends {@link ViewHolderBase}
     */
    public ListViewDataAdapterBase(ViewHolderCreator<T> viewHolderCreator) {
        mViewHolderCreator = viewHolderCreator;
    }

    public void setViewHolderCreator(ViewHolderCreator<T> viewHolderCreator) {
        mViewHolderCreator = viewHolderCreator;
    }

    public void setViewHolderClass(final Object enclosingInstance, final Class<?> cls, final Object... args) {
        mLazyCreator = LazyViewHolderCreator.create(enclosingInstance, cls, args);
    }

    private ViewHolderBase<T> createViewHolder(int position) {
        if (mViewHolderCreator == null && mLazyCreator == null) {
            throw new RuntimeException("view holder creator is null");
        }
        if (mViewHolderCreator != null) {
            return mViewHolderCreator.createViewHolder(position);
        }
        if (mLazyCreator != null) {
            return mLazyCreator.createViewHolder(position);
        }
        return null;
    }

    public void forceCreateView(boolean yes) {
        mForceCreateView = yes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T itemData = getItem(position);
        ViewHolderBase<T> holderBase = null;
        if (mForceCreateView || convertView == null || (!(convertView.getTag() instanceof ViewHolderBase<?>))) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            holderBase = createViewHolder(position);
            if (holderBase != null) {
                convertView = holderBase.createView(inflater);
                if (convertView != null) {
                    if (!mForceCreateView) {
                        convertView.setTag(holderBase);
                    }
                }
            }
        } else {
            holderBase = (ViewHolderBase<T>) convertView.getTag();
        }
        if (holderBase != null) {
            holderBase.setItemData(position, convertView);
            holderBase.showData(position, itemData);
        }
        return convertView;
    }

    @Override
    public abstract T getItem(int position);
}
