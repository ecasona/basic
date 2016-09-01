package com.ecasona.library.atapter;

/**
 * Created by aiy on 2016/9/1.
 */

public interface ViewHolderCreator<T> {

    ViewHolderBase<T> createViewHolder(int position);
}
