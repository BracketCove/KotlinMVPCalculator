package com.wiseassblog.kotlincalculator.dependencyinjection

import android.app.Application
import android.content.Context
import com.wiseassblog.kotlincalculator.data.CalculatorImpl
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides Feature independent Dependencies (things not necessarily coupled to a specific feature
 * like Context, Database, Services, etc.)
 * Created by R_KAY on 1/3/2018.
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideCalculator():ICalculator{
        return CalculatorImpl()
    }

}