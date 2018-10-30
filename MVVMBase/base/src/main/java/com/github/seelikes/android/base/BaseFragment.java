package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yatoooon.screenadaptation.conversion.CustomConversion;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment<C extends BaseFragment<C, M, B>, M extends BaseHostViewModel<C, M, B>, B extends ViewDataBinding> extends Fragment implements BaseViewModel.ViewBinderLister<M, B> {
    private CompositeDisposable disposable;
    public M model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        disposable = new CompositeDisposable();
        model = initModel(inflater, container, savedInstanceState, disposable);
        return model.binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }

    @Override
    public void beforeModelSetToBinding(M model, B binding) {

    }

    protected abstract M initModel(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, CompositeDisposable disposable);

    @Override
    public void afterModelSetToBinding(M model, B binding) {

    }
}
