package com.ecasona.library.loading;

import android.content.Context;
import android.view.View;

/**
 * Created by AiYang on 2016/8/29.
 * <p>
 * des: 等待弹窗规范
 */
public interface IVaryViewHelper {

    View getCurrentLayout();

    void showLayout(View view);

    void restoreView();

    View inflate(int layoutId);

    Context getContext();

    View getView();

}
