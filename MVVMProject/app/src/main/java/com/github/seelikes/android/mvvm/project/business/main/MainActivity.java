package com.github.seelikes.android.mvvm.project.business.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.github.seelikes.android.base.BaseActivity;
import com.github.seelikes.android.mvvm.project.R;
import com.github.seelikes.android.mvvm.project.databinding.ActivityMainBinding;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity<MainActivity, MainViewModel, ActivityMainBinding> {
    @Override
    protected MainViewModel initModel(@Nullable Bundle savedInstanceState, CompositeDisposable disposable) {
        return new MainViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_main), disposable);
    }
}
