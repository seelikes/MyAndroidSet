package com.example.myjetpackapplication.business.kotlinBasic.mutex

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.kotlinBasic.mutex.databinding.ActivityMutexBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class MutexViewModel(host: MutexActivity, binding: ActivityMutexBinding) : BasicHostViewModel<MutexViewModel, MutexActivity, ActivityMutexBinding>(host, binding) {
    val title = ObservableInt(R.string.kotlinBasic_mutex_title)
}