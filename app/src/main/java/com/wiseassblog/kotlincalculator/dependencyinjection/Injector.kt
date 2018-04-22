package com.wiseassblog.kotlincalculator.dependencyinjection

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.wiseassblog.kotlincalculator.data.CalculatorImpl
import com.wiseassblog.kotlincalculator.data.ValidatorImpl
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.util.scheduler.SchedulerProviderImpl
import com.wiseassblog.kotlincalculator.view.CalculatorFragment
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorViewModel

/**
 * Basic DI implementation.
 * Created by R_KAY on 1/28/2018.
 */
class Injector(private var activity:AppCompatActivity) {

    private var validator: ValidatorImpl = ValidatorImpl
    private var calculator: CalculatorImpl = CalculatorImpl
    private var schedulerProvider: SchedulerProviderImpl = SchedulerProviderImpl


    fun providePresenter(view: CalculatorFragment): IViewContract.Presenter {
        return CalculatorPresenter(
                view,
                ViewModelProviders.of(activity).get(CalculatorViewModel::class.java),
                schedulerProvider,
                EvaluateExpression(calculator, validator, schedulerProvider)
        )
    }
}