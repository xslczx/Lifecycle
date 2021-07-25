package com.xslczx.lifecycle;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public abstract class BaseActivity extends ActivityImpl implements ILifecycleDelegate {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSourceCreated(savedInstanceState);
        if (getSourceLayout() != 0) {
            setContentView(getSourceLayout());
        }
    }

    /**
     * 设置完布局后的回调
     */
    @Override
    public final void onContentChanged() {
        super.onContentChanged();
        ViewGroup contentParent = findViewById(android.R.id.content);
        onSourceViewCreated(contentParent.getChildAt(0));
    }

    /**
     * 覆写资源布局或onCreate()至少一个
     */
    protected int getSourceLayout() {
        return 0;
    }

    @Override
    protected final void onStart() {
        super.onStart();
    }

    /**
     * create完成 View完全准备/恢复好
     */
    @Override
    protected final void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onSourceViewAttached(savedInstanceState);
    }

    @Override
    protected final void onResume() {
        super.onResume();
        onSourceStarted();
    }

    @Override
    protected final void onStop() {
        super.onStop();
        onSourceStopped();
        onSourceViewDetached();
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        onSourceDestroyed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        onSourceViewVisibilityChanged(hasFocus);
    }
}
