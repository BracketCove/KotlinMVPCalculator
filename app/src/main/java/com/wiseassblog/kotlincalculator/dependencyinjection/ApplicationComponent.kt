package com.wiseassblog.kotlincalculator.dependencyinjection

import com.wiseassblog.kotlincalculator.KotlinCalculator
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by R_KAY on 1/3/2018.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
ApplicationModule::class,
FragmentBuilder::class)
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder
    {
        @BindsInstance
        fun application(application: KotlinCalculator): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: KotlinCalculator)
}