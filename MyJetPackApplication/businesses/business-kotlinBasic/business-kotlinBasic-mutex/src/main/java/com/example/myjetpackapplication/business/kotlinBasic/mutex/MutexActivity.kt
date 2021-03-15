package com.example.myjetpackapplication.business.kotlinBasic.mutex

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.kotlinBasic.mutex.databinding.ActivityMutexBinding
import com.example.myjetpackapplication.business.kotlinBasic.mutex.ui.ConsoleAdapter
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

@Business(path = "/business/kotlinBasic/mutex", title = "kotlinBasic_mutex_title", parent = "kotlinBasic_title")
class MutexActivity : BasicActivity<MutexActivity, MutexViewModel, ActivityMutexBinding>() {
    private var scrollJob: Job? = null

    override fun initModel(savedInstanceState: Bundle?): MutexViewModel {
        return MutexViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_mutex))
    }

    override fun initView(binding: ActivityMutexBinding) {
        super.initView(binding)
        binding.consoleRecycler.run {
            layoutManager = LinearLayoutManager(this@MutexActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ConsoleAdapter(this@MutexActivity, listOf(), lifecycleScope)
        }
    }

    fun on2TimersClick() {
        binding.twoTimers.isEnabled = false
        Logger.i("on2TimersClick.111222, enter")

        val mutex = Mutex()
        val timer1 = object : CountDownTimer(10000L, 400L) {
            override fun onFinish() {
                (binding.consoleRecycler.adapter as? ConsoleAdapter)?.addItem("Timer1 Done!!!")
            }

            override fun onTick(millisUntilFinished: Long) {
                Logger.i("Timer1,111222, millisUntilFinished: $millisUntilFinished")
                (binding.consoleRecycler.adapter as? ConsoleAdapter)?.addItem("Timer1 $millisUntilFinished")
            }
        }
        timer1.start()

        if (scrollJob?.isActive == true) {
            return
        }
        scrollJob = lifecycleScope.launch {
            binding.consoleRecycler.smoothScrollToPosition((binding.consoleRecycler.adapter?.itemCount ?: 1) - 1)
            delay(1000)
        }
    }
}