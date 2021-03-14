package com.example.myjetpackapplication.business.kotlinBasic.mutex

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.kotlinBasic.mutex.databinding.ActivityMutexBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/kotlinBasic/mutex", title = "kotlinBasic_mutex_title", parent = "kotlinBasic_title")
class MutexActivity : BasicActivity<MutexActivity, MutexViewModel, ActivityMutexBinding>() {
    override fun initModel(savedInstanceState: Bundle?): MutexViewModel {
        return MutexViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_mutex))
    }
}