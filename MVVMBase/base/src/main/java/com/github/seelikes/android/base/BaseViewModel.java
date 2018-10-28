package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.java.lib.oil.lang.reflect.ReflectManager;

import java.lang.reflect.InvocationTargetException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BaseViewModel<M extends BaseViewModel<M, B>, B extends ViewDataBinding> {
    public CompositeDisposable disposable;
    public B binding;
    protected ViewBinderLister<M, B> listener;

    public interface ViewBinderLister<MM extends BaseViewModel<MM, MB>, MB extends ViewDataBinding> {
        void beforeModelSetToBinding(MM model, MB binding);
        void afterModelSetToBinding(MM model, MB binding);
    }

    @SuppressWarnings("unchecked")
    public BaseViewModel(B binding, @Nullable ViewBinderLister<M, B> listener, CompositeDisposable disposable) {
        this.binding = binding;
        this.listener = listener;
        this.disposable = disposable;
        disposable.add(
            Flowable.just(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(model -> {
                    beforeModelSetToBinding((M) this, this.binding);
                    try {
                        if (this.listener != null) {
                            this.listener.beforeModelSetToBinding((M) this, binding);
                        }
                        ReflectManager.getInstance().invoke(binding.getClass(), "setModel", this.binding, this);
                        if (this.listener != null) {
                            this.listener.afterModelSetToBinding((M) this, binding);
                        }
                    }
                    catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    afterModelSetToBinding((M) this, this.binding);
                })
        );
    }

    public void beforeModelSetToBinding(M model, B binding) {

    }

    public void afterModelSetToBinding(M model, B binding) {

    }
}
