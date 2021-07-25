package com.xslczx.lifecycle.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.xslczx.lifecycle.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}