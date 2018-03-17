package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.util.scheduler.BaseSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Allows testing of RxJava impl on JVM (we can't use an AndroidScheduler on the JVM).
 * Created by R_KAY on 12/20/2017.
 */
class TestScheduler: BaseSchedulerProvider {
    override fun getComputationScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getUiScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}