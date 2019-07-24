package com.example.myjetpackapplication.utils

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import com.github.seelikes.android.mvvm.basic.BasicViewHolder

/**
 * Created by liutiantian on 2019-05-07 15:26 星期二
 */
fun <T : Any, VH: BasicViewHolder<T, B>, B : ViewDataBinding> connect(adapter: BasicRecyclerAdapter<T, VH, B>, items: ObservableArrayList<T>) {
    items.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<T>>() {
        override fun onChanged(sender: ObservableArrayList<T>?) {
            adapter.notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) {
            adapter.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(sender: ObservableArrayList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            for (i in 0 until itemCount) {
                adapter.notifyItemMoved(fromPosition + i, toPosition + i)
            }
        }

        override fun onItemRangeInserted(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) {
            for (i in 0 until itemCount) {
                adapter.notifyItemInserted(positionStart + i)
            }
        }

        override fun onItemRangeChanged(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) {
            adapter.notifyItemRangeChanged(positionStart, itemCount)
        }
    })
}