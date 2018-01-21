package com.wiseassblog.kotlincalculator.dependencyinjection

import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.util.SchedulerProviderImpl
import com.wiseassblog.kotlincalculator.view.CalculatorFragment
import com.wiseassblog.kotlincalculator.view.IViewContract
import dagger.Module
import dagger.Provides

/**
 * Created by R_KAY on 1/3/2018.
 */
@Module
class CalculatorModule {

    @Provides
    fun provideView (view:CalculatorFragment):IViewContract.View {
        return view
    }

    @Provides
    fun providePresenter (view:IViewContract.View, eval: EvaluateExpression):IViewContract.Presenter {
        return CalculatorPresenter(view, SchedulerProviderImpl(), eval)
    }

}