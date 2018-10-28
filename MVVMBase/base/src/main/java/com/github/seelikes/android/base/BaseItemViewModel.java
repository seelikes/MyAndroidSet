package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

public class BaseItemViewModel<T, M extends BaseViewModel<M, B>, B extends ViewDataBinding> extends BaseViewModel<M, B> {
    public T item;

    public BaseItemViewModel(B binding, @Nullable ViewBinderLister<M, B> listener, CompositeDisposable disposable) {
        super(binding, listener, disposable);
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
        binding.invalidateAll();
    }
}
