package com.wiseassblog.kotlincalculator.dependencyinjection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.wiseassblog.kotlincalculator.data.implementations.CalculatorImpl
import com.wiseassblog.kotlincalculator.data.implementations.EvaluatorImpl
import com.wiseassblog.kotlincalculator.data.implementations.ValidatorImpl
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.util.DispatcherProvider
import com.wiseassblog.kotlincalculator.view.CalculatorFragment
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorViewModel

/**
 * Basic DI implementation.
 * Created by R_KAY on 1/28/2018.
 */
class Injector(private var activity: AppCompatActivity) {

    private var validator: ValidatorImpl = ValidatorImpl
    private var calculator: CalculatorImpl = CalculatorImpl

    fun providePresenter(view: CalculatorFragment): IViewContract.Presenter {
        return CalculatorPresenter(
                view,
                ViewModelProviders.of(activity).get(CalculatorViewModel::class.java),
                EvaluatorImpl(calculator, validator),
                DispatcherProvider
        )
    }
}