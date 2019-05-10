package com.example.myjetpackapplication.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.myjetpackapplication.utils.runIfClassHasAnnotation
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BasicFragment<C : BasicFragment<C, M, B>, M : BasicHostViewModel<M, C, B>, B : ViewDataBinding> : Fragment(), BasicInitView<B> {
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var model: M

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
        initView(model.binding)
        return model.binding.root
    }

    @CallSuper
    override fun onDestroy() {
        runIfClassHasAnnotation(Subscribe::class.java) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }

    /**
     * 初始化model
     */
    abstract fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): M
}