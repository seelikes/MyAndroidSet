package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yatoooon.screenadaptation.conversion.CustomConversion;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseAdapter<T, M extends BaseItemViewModel<T, M, B>, B extends ViewDataBinding> extends android.widget.BaseAdapter {
    protected CompositeDisposable disposable;
    private List<T> data;

    public BaseAdapter(List<T> data, CompositeDisposable disposable) {
        this.data = data;
        this.disposable = disposable;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.data != null ? this.data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        M model;
        if (convertView == null) {
            model = initModel(LayoutInflater.from(parent.getContext()), parent, this.data.get(position), disposable);
            if (model.binding.getRoot() instanceof ViewGroup) {
                ScreenAdapterTools.getInstance().loadView((ViewGroup) model.binding.getRoot(), new CustomConversion());
            }
            model.binding.getRoot().setTag(model);
        }
        else {
            model = (M) convertView.getTag();
        }
        model.setItem(this.data.get(position));
        return model.binding.getRoot();
    }

    protected abstract M initModel(LayoutInflater inflater, ViewGroup parent, T data, CompositeDisposable disposable);
}
