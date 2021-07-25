package com.xslczx.lifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public class FragmentImpl extends Fragment implements ILifecycleDelegate {

    @Override
    public void onSourceCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onSourceViewCreated(@NonNull View view) {

    }

    @Override
    public void onSourceViewAttached(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onSourceViewVisibilityChanged(boolean visible) {

    }

    @Override
    public void onSourceViewDetached() {

    }

    @Override
    public void onSourceStarted() {

    }

    @Override
    public void onSourceStopped() {

    }

    @Override
    public void onSourceDestroyed() {

    }
}
