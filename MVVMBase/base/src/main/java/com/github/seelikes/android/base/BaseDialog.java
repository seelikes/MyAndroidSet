package com.github.seelikes.android.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseDialog<C extends BaseDialog<C, M, B>, M extends BaseHostViewModel<C, M, B>, B extends ViewDataBinding> extends Dialog implements BaseViewModel.ViewBinderLister<M, B> {
    protected CompositeDisposable disposable;
    public M model;

    public BaseDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        disposable = new CompositeDisposable();
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
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

    public abstract M initModel(Bundle savedInstanceState, CompositeDisposable disposable);

    @Override
    public void afterModelSetToBinding(M model, B binding) {

    }
}
