package com.example.myjetpackapplication.business.profiler.cpu

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.databinding.ActivityCpuBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Route(path = "/business/cpu")
class CPUActivity : BasicActivity<CPUActivity, CpuViewModel, ActivityCpuBinding>() {
    override fun initModel(savedInstanceState: Bundle?): CpuViewModel = CpuViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_cpu))
}