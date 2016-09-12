package com.ecasona.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecasona.library.loading.VaryViewHelperController;
import com.ecasona.library.netstatus.NetChangeObserver;
import com.ecasona.library.netstatus.NetUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by AiYang on 2016/8/31.
 * <p>
 * Description:
 */
public abstract class BaseLazyFragment extends Fragment {

    /**
     * log tag
     */
    protected static String TAG_LOG = null;

    /**
     * context
     */
    protected Context context = null;

    /**
     * loading view controller
     */
    private VaryViewHelperController varyViewHelperController = null;

    private boolean isPrepared = false;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    protected Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (0 != getContentViewLayoutId()) {
            return inflater.inflate(getContentViewLayoutId(), null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO 绑定注解（butterKnife）
        unbinder = ButterKnife.bind(this,view);
        if (null != getLoadingTargetView()) {
            varyViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        initViewAndEvents();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = BaseLazyFragment.this.getClass().getSimpleName();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //TODO butterKnife 解绑
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    /**
     * 数据懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstInvisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInVisible();
            } else {
                onUserInVisible();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInVisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    /**
     * this method like the fragment's lifecycle method onPause();
     */
    protected abstract void onUserInVisible();

    /**
     * when the fragment is invisible for the first time
     */
    private void onFirstUserInVisible() {
        //here wo do no recommend do anything
    }

    /**
     * this method like the fragment's lifecycle method onResume();
     */
    protected abstract void onUserVisible();

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * when fragment is visible for the first time,here we can do some initialized work refresh data only once
     */
    protected abstract void onFirstUserVisible();

    protected abstract View getLoadingTargetView();

    /**
     * 控件实例化，以及事件注册
     */
    protected abstract void initViewAndEvents();

    /**
     * 获取activity 对应的布局
     *
     * @return 对应id
     */
    protected abstract int getContentViewLayoutId();

    /**
     * activity 的传值
     *
     * @param bundle
     */
    protected abstract void getBundleExtras(Bundle bundle);

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void goActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     */
    protected void goActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void goActivityThenFinish(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     */
    protected void goActivityThenFinish(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void goActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void goActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * show toast
     *
     * @param msg message
     */
    protected abstract void showToast(String msg);

    /**
     * toggle show loading
     *
     * @param toggle
     * @param msg
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == varyViewHelperController) {
            throw new IllegalArgumentException("You mast return a right target view for loading");
        }
        if (toggle) {
            varyViewHelperController.showLoading(msg);
        } else {
            varyViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle
     * @param msg
     * @param clickListener
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener clickListener) {
        if (null == varyViewHelperController) {
            throw new IllegalArgumentException("You mast return a right target view for loading");
        }
        if (toggle) {
            varyViewHelperController.showEmpty(msg, clickListener);
        } else {
            varyViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     * @param msg
     * @param onClickListener
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == varyViewHelperController) {
            throw new IllegalArgumentException("You mast return a right target view for loading");
        }
        if (toggle) {
            varyViewHelperController.showError(msg, onClickListener);
        } else {
            varyViewHelperController.restore();
        }
    }

    protected void toggleShowNetworkError(boolean toogle, View.OnClickListener onClickListener) {
        if (null == varyViewHelperController) {
            throw new IllegalArgumentException("You mast return a right target view for loading");
        }
        if (toogle) {
            varyViewHelperController.showNetworkError(onClickListener);
        } else {
            varyViewHelperController.restore();
        }
    }

}
