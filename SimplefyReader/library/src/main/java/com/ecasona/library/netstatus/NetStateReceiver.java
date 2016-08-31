package com.ecasona.library.netstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ecasona.library.utils.ELog;

import java.util.ArrayList;

/**
 * Created by AiYang on 2016/8/30.
 * <p>
 * Description:网络连接监听
 */
public class NetStateReceiver extends BroadcastReceiver {

    private static final String TAG = NetStateReceiver.class.getSimpleName();

    public final static String CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.ecasona.library.net.conn.CONNECTIVITY_CHANGE";
    private static final String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    private static boolean isNetAvaliable = false;
    private static NetUtils.NetType netType;
    private static ArrayList<NetChangeObserver> netChangeObservers = new ArrayList<>();
    private static BroadcastReceiver broadcastReceiver;

    private static BroadcastReceiver getReceiver() {
        if (null == broadcastReceiver) {
            synchronized (NetStateReceiver.class) {
                broadcastReceiver = new NetStateReceiver();
            }
        }
        return broadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        broadcastReceiver = NetStateReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(CUSTOM_ANDROID_NET_CHANGE_ACTION)) {
            if (NetUtils.isNetworkAvailable(context)) {
                ELog.e(TAG, "<--- network disconnected --->");
                isNetAvaliable = false;
            } else {
                ELog.e(TAG, "<--- network connected --->");
                isNetAvaliable = true;
                netType = NetUtils.getAPNType(context);
            }
            notifyObserver();
        }
    }

    public static void registernetworkStateReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        context.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    public static void checkNetworkState(Context context) {
        Intent intent = new Intent();
        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        context.sendBroadcast(intent);
    }

    public static void unRegisterNetworkStateReceiver(Context context) {
        if (null != broadcastReceiver) {
            try {
                context.getApplicationContext().unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
                ELog.e(TAG, e.getMessage());
            }
        }
    }

    public static boolean isNetworkAvaliable() {
        return isNetAvaliable;
    }

    public static NetUtils.NetType getAPNType() {
        return netType;
    }

    private void notifyObserver() {
        if (!netChangeObservers.isEmpty()) {
            int size = netChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetChangeObserver observer = netChangeObservers.get(i);
                if (null != observer) {
                    if (isNetworkAvaliable()) {
                        observer.onNetConnected(netType);
                    } else {
                        observer.onNetDisConnect();
                    }
                }
            }
        }
    }

    public static void registerObserver(NetChangeObserver observer) {
        if (null == netChangeObservers) {
            netChangeObservers = new ArrayList<>();
        }
        netChangeObservers.add(observer);
    }

    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (null != netChangeObservers) {
            if (netChangeObservers.contains(observer)) {
                netChangeObservers.remove(observer);
            }
        }
    }
}
