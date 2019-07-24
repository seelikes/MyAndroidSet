package com.github.seelikes.android.mvvm.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.github.seelikes.android.mvvm.basic.utils.runIfClassHasAnnotation
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BasicDialog<C : BasicDialog<C, M, B>, M : BasicHostViewModel<M, C, B>, B : ViewDataBinding> : DialogFragment(), BasicInitView<B> {
    /**
     * Model对象
     */
    protected lateinit var model: M

    /**
     * ViewBinding对象
     */
    protected lateinit var binding: B

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runIfClassHasAnnotation(Subscribe::class.java) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        model = initModel(inflater, container, savedInstanceState)
        binding = model.binding
        initView(model.binding)
        return model.binding.root
    }

    @CallSuper
    override fun dismiss() {
        runIfClassHasAnnotation(Subscribe::class.java) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.dismiss()
    }

    /**
     * 初始化model
     */
    abstract fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): M
}