package com.example.myjetpackapplication.business.kotlinBasic.mutex.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.kotlinBasic.mutex.databinding.ItemMutexConsoleBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import kotlinx.coroutines.CoroutineScope
import java.lang.ref.WeakReference

class ConsoleVH(weakContext: WeakReference<Context>, binding: ItemMutexConsoleBinding, routineScope: CoroutineScope?) : BasicViewHolder<String, ItemMutexConsoleBinding>(weakContext, binding, routineScope) {
    override fun setData(entity: String?) {
        super.setData(entity)
        binding.consoleText.text = entity
    }
}

class ConsoleAdapter(context: Context, items: List<String>, routineScope: CoroutineScope) : BasicRecyclerAdapter<String, ConsoleVH>(context, items, null, routineScope) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsoleVH {
        return ConsoleVH(WeakReference(context), ItemMutexConsoleBinding.inflate(LayoutInflater.from(context), parent, false), routineScope)
    }

    fun addItem(item: String) {
        items = (items?.toMutableList() ?: mutableListOf()).apply {
            add(item)
        }
        notifyItemInserted(items?.size ?: return)
    }
}