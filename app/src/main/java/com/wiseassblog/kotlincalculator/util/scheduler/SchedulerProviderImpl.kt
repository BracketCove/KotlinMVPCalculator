package com.wiseassblog.kotlincalculator.util.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by R_KAY on 12/20/2017.
 */
object SchedulerProviderImpl: BaseSchedulerProvider {

    override fun getComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

    override fun getUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}