package com.example.myjetpackapplication.sophix.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.orhanobut.logger.Logger
import com.taobao.sophix.SophixManager

/**
 * Created by liutiantian on 2019-05-14 23:50 星期二
 */
class SophixWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        Logger.i("SophixWorker.UL4886122304953LP, doWork, enter")
        SophixManager.getInstance().queryAndLoadNewPatch()
        return Result.success()
    }
}