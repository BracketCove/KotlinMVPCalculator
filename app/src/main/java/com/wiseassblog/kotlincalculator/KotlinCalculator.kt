package com.wiseassblog.kotlincalculator

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by R_KAY on 1/3/2018.
 */
class KotlinCalculator: Application() {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        LeakCanary.install(this)
    }
}