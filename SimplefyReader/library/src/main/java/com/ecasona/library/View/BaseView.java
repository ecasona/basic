package com.ecasona.library.view;

/**
 * Created by aiy on 2016/9/8.
 * <p>
 * des:基本规范
 */

public interface BaseView {

    /**
     * show loadong message(显示弹窗)
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏弹窗
     */
    void hideLoading();

    /**
     * show error message
     *
     * @param msg
     */
    void showError(String msg);

    /**
     * show exception message
     *
     * @param msg
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();
}
