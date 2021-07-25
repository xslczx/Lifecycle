package com.xslczx.lifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseFragment extends FragmentImpl implements ILifecycleDelegate {
    private boolean mUserVisible = false;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSourceCreated(savedInstanceState);
    }

    /**
     * 覆写资源布局或getSourceView至少一个
     */
    protected abstract int getSourceLayout();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        if (getSourceLayout() <= 0) return null;
        View view = null;
        try {
            view = inflater.inflate(getSourceLayout(), container, false);
        } catch (Exception ignore) {
        }
        return view;
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onSourceViewCreated(view);
    }

    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isAdded()) { // 未被添加前的回调忽略
            if (isVisibleToUser && !mUserVisible) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && mUserVisible) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onSourceViewAttached(savedInstanceState);
    }

    @Override
    public final void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        dispatchUserVisibleHint(!hidden);
    }

    @Override
    public final void onStart() {
        super.onStart();
        onSourceStarted();
    }

    @Override
    public final void onResume() {
        super.onResume();
        if (!isHidden() && !mUserVisible && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public final void onPause() {
        super.onPause();
        if (mUserVisible && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public final void onStop() {
        super.onStop();
        onSourceStopped();
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        onSourceViewDetached();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        onSourceDestroyed();
    }

    /**
     * 可见性分发
     */
    private void dispatchUserVisibleHint(boolean visible) {
        if (visible && !isParentVisible()) {
            return;
        }
        if (mUserVisible == visible) {
            return;
        }
        mUserVisible = visible;
        if (visible) {
            onSourceViewVisibilityChanged(true);
            dispatchChildVisibleState(true);
        } else {
            dispatchChildVisibleState(false);
            onSourceViewVisibilityChanged(false);
        }
    }

    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return true;
        }
        if (fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            return baseFragment.isSupportUserVisible();
        }
        return fragment.isVisible();
    }

    private boolean isSupportUserVisible() {
        return mUserVisible;
    }

    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment child : fragments) {
                if (child instanceof BaseFragment && !child.isHidden() && child.getUserVisibleHint()) {
                    ((BaseFragment) child).dispatchUserVisibleHint(visible);
                }
            }
        }
    }
}
