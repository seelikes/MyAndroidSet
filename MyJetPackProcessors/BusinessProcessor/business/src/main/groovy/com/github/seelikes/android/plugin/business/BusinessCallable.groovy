package com.github.seelikes.android.plugin.business

import org.gradle.api.GradleException

import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch

/**
 * Created by liutiantian on 2020-11-22 02:05 星期日
 */
class BusinessCallable implements Callable<Boolean> {
    private Runnable runnable
    private CountDownLatch countDown;
    private boolean silent

    BusinessCallable(Runnable runnable, CountDownLatch countDown, boolean silent) {
        this.runnable = runnable
        this.countDown = countDown
        this.silent = silent
    }

    @Override
    Boolean call() throws Exception {
        try {
            if (runnable != null) {
                runnable.run()
            }
        }
        catch (Throwable throwable) {
            if (!silent) {
                throw new GradleException("Error in threads", throwable)
            }
        }
        finally {
            countDown.countDown()
        }
        return true
    }
}
