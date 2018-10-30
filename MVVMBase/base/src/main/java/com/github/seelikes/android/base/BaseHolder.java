package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseHolder<T, C extends BaseHolder<T, C, M, B>, M extends BaseRecyclerViewModel<T, M, C, B>, B extends ViewDataBinding> extends RecyclerView.ViewHolder implements BaseViewModel.ViewBinderLister<M, B> {
    public CompositeDisposable disposable;
    public M model;

    public BaseHolder(@NonNull B binding, CompositeDisposable disposable) {
        super(binding.getRoot());
        this.disposable = disposable;
        model = initModel(binding, disposable);
    }

    public void onItem(T item) {
        model.setItem(item);
    }

    public abstract M initModel(B binding, CompositeDisposable disposable);

    @Override
    public void beforeModelSetToBinding(M model, B binding) {

    }

    @Override
    public void afterModelSetToBinding(M model, B binding) {

    }
}
