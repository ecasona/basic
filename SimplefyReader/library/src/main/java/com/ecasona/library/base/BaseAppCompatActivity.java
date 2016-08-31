package com.ecasona.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ecasona.library.loading.VaryViewHelperController;
import com.ecasona.library.netstatus.NetChangeObserver;
import com.ecasona.library.netstatus.NetStateReceiver;
import com.ecasona.library.netstatus.NetUtils;


/**
 * Created by AiYang on 2016/8/29.
 * <p/>
 * des:
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    /**
     * log tag
     */
    protected static String TAG_LOG = null;

    /**
     * context
     */
    protected Context context = null;

    /**
     * network
     */
    protected NetChangeObserver mNetChangeObserver = null;

    /**
     * loading view controller
     */
    private VaryViewHelperController varyViewHelperController = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            getBundleExtras(bundle);
        }

        context = this;
        TAG_LOG = this.getClass().getSimpleName();

        BaseAppManager.getInstance().addActivity(this);

        if (getContentViewLayoutId() != 0) {
            setContentView(getContentViewLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id.");
        }

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };
        //每个activity 监听网络连接情况
        NetStateReceiver.registerObserver(mNetChangeObserver);

        initViewAndEvents();

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if (null != getLoadingTargetView()) {
            varyViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }

    protected abstract View getLoadingTargetView();

    /**
     * 控件实例化，以及事件注册
     */
    protected abstract void initViewAndEvents();

    /**
     * 网络未连接处理
     */
    protected abstract void onNetworkDisConnected();

    /**
     * 网络连接处理
     *
     * @param type
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

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
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     */
    protected void goActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
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
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     */
    protected void goActivityThenFinish(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void goActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
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
        Intent intent = new Intent(this, clazz);
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

    protected void toggleShowNetworkError(boolean toogle, String msg, View.OnClickListener onClickListener) {
        if (null == varyViewHelperController) {
            throw new IllegalArgumentException("You mast return a right target view for loading");
        }
        if (toogle) {
            varyViewHelperController.showNetworkError(msg, onClickListener);
        } else {
            varyViewHelperController.restore();
        }
    }
}
