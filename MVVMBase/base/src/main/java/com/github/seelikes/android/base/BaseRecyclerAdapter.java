package com.github.seelikes.android.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseRecyclerAdapter<T, H extends BaseHolder<T, H, M, B>, M extends BaseRecyclerViewModel<T, M, H, B>, B extends ViewDataBinding> extends RecyclerView.Adapter<H> {
    protected CompositeDisposable disposable;
    protected List<T> items;

    public BaseRecyclerAdapter(List<T> items, CompositeDisposable disposable) {
        this.items = items;
        this.disposable = disposable;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return getHolder(viewGroup, items.get(position), position, disposable);
    }

    public abstract H getHolder(@NonNull ViewGroup viewGroup, T data, int position, CompositeDisposable disposable);

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        holder.onItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.items != null ? this.items.size() : 0;
    }
}
