package com.xslczx.lifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 载体:Activity/Fragment ,View:根视图
 */
public interface ILifecycleDelegate {
    /**
     * 载体被创建 此处做全局变量、资源等初始化操作
     */
    void onSourceCreated(@Nullable Bundle savedInstanceState);

    /**
     * View被创建 此处做视图相关初始化操作
     */
    void onSourceViewCreated(@NonNull View view);

    /**
     * View添加到载体 View完全准备/恢复好 Bundle中是恢复的数据，仅做少量注册监听、检查Intent、Bundle等数据操作
     */
    void onSourceViewAttached(@Nullable Bundle savedInstanceState);

    /**
     * View可见性变化 检查网络、蓝牙连接等状态、恢复暂停操作等
     */
    void onSourceViewVisibilityChanged(boolean visible);

    /**
     * View从载体移除，注销监听
     */
    void onSourceViewDetached();

    /**
     * 载体活动中
     */
    void onSourceStarted();

    /**
     * 载体停止活动
     */
    void onSourceStopped();

    /**
     * 载体销毁，资源释放
     */
    void onSourceDestroyed();
}
