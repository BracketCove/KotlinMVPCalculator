package com.wiseassblog.kotlincalculator.dependencyinjection

import com.wiseassblog.kotlincalculator.CalculatorActivity
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.view.CalculatorFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by R_KAY on 1/3/2018.
 */
@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = arrayOf(CalculatorModule::class))
    abstract fun bindCalculatorFragment(): CalculatorFragment

}
