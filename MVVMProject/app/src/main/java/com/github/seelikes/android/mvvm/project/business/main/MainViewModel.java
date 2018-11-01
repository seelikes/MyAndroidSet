package com.github.seelikes.android.mvvm.project.business.main;

import com.github.seelikes.android.base.BaseHostViewModel;
import com.github.seelikes.android.mvvm.project.databinding.ActivityMainBinding;

import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends BaseHostViewModel<MainActivity, MainViewModel, ActivityMainBinding> {
    public MainViewModel(MainActivity host, ActivityMainBinding binding, CompositeDisposable disposable) {
        super(host, binding, disposable);
    }
}
