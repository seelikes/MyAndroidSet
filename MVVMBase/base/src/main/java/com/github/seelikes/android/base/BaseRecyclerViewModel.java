package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;

import io.reactivex.disposables.CompositeDisposable;

public class BaseRecyclerViewModel<T, C extends BaseRecyclerViewModel<T, C, H, B>, H extends BaseHolder<T, H, C, B>, B extends ViewDataBinding> extends BaseHostViewModel<H, C, B> {
    public T item;

    public BaseRecyclerViewModel(H host, B binding, CompositeDisposable disposable) {
        super(host, binding, disposable);
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
        binding.invalidateAll();
    }
}
