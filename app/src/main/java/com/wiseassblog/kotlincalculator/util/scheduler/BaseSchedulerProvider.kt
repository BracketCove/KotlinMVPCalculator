package com.wiseassblog.kotlincalculator.util.scheduler

import io.reactivex.Scheduler

/**
 * Created by R_KAY on 12/20/2017.
 */
interface BaseSchedulerProvider {

    fun getComputationScheduler(): Scheduler

    fun getUiScheduler(): Scheduler
}