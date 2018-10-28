package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;

import io.reactivex.disposables.CompositeDisposable;

public class BaseHostViewModel<H extends BaseViewModel.ViewBinderLister<M, B>, M extends BaseHostViewModel<H, M, B>, B extends ViewDataBinding> extends BaseViewModel<M, B> {
    public H host;

    public BaseHostViewModel(H host, B binding, CompositeDisposable disposable) {
        super(binding, host, disposable);
        this.host = host;
    }
}
