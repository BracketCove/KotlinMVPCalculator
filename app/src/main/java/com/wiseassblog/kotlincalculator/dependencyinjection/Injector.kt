package com.wiseassblog.kotlincalculator.dependencyinjection

import com.wiseassblog.kotlincalculator.data.CalculatorImpl
import com.wiseassblog.kotlincalculator.data.ValidatorImpl
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.domain.repository.IValidator
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.util.SchedulerProviderImpl
import com.wiseassblog.kotlincalculator.view.IViewContract

/**
 * Basic DI implementation.
 * Created by R_KAY on 1/28/2018.
 */
object Injector {

    private var validator: ValidatorImpl = ValidatorImpl()
    private var calculator: CalculatorImpl = CalculatorImpl()
    private var schedulerProvider: SchedulerProviderImpl = SchedulerProviderImpl()

    //below
    fun provideValidator(): IValidator {
        return validator
    }

    fun provideCalculator(): ICalculator {
        return calculator
    }

    fun providePresenter(view: IViewContract.View): IViewContract.Presenter {
        return CalculatorPresenter(
                view,
                schedulerProvider,
                EvaluateExpression(calculator, validator, schedulerProvider)
        )
    }


}