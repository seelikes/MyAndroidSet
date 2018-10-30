package com.github.seelikes.android.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseSheetDialog<C extends BaseSheetDialog<C, M, B>, M extends BaseHostViewModel<C, M, B>, B extends ViewDataBinding> extends BottomSheetDialog implements BaseViewModel.ViewBinderLister<M, B> {
    public CompositeDisposable disposable;
    public M model;

    public BaseSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    protected BaseSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        disposable = new CompositeDisposable();
    }

    public BaseSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
        disposable = new CompositeDisposable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = initModel(savedInstanceState, disposable);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        disposable.dispose();
    }

    @Override
    public void beforeModelSetToBinding(M model, B binding) {

    }

    protected abstract M initModel(Bundle savedInstanceState, CompositeDisposable disposable);

    @Override
    public void afterModelSetToBinding(M model, B binding) {

    }
}
