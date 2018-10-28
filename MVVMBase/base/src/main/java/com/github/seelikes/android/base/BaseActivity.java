package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yatoooon.screenadaptation.conversion.CustomConversion;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity<C extends BaseActivity<C, M, B>, M extends BaseHostViewModel<C, M, B>, B extends ViewDataBinding> extends AppCompatActivity implements BaseHostViewModel.ViewBinderLister<M, B> {
    protected CompositeDisposable disposable;
    public M model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposable = new CompositeDisposable();
        model = initModel(savedInstanceState, disposable);
        if (model.binding.getRoot() instanceof ViewGroup) {
            ScreenAdapterTools.getInstance().loadView((ViewGroup) model.binding.getRoot(), new CustomConversion());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    public void beforeModelSetToBinding(M model, B binding) {

    }

    /**
     * it's highly recommended that you use this class with data binding enabled
     * but you can ignore data binding with a null return
     * @return the activity's model
     */
    protected abstract M initModel(@Nullable Bundle savedInstanceState, CompositeDisposable disposable);

    @Override
    public void afterModelSetToBinding(M model, B binding) {

    }
}
