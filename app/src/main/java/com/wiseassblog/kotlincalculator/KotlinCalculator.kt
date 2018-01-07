package com.wiseassblog.kotlincalculator

import android.app.Activity
import android.app.Application
import com.wiseassblog.kotlincalculator.dependencyinjection.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by R_KAY on 1/3/2018.
 */
class KotlinCalculator: Application(), HasActivityInjector {

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

    }

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

}