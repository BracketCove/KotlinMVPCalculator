package com.wiseassblog.kotlincalculator.data

import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.viewmodel.DisplayVM
import io.reactivex.Flowable

/**
 * Created by R_KAY on 12/21/2017.
 */
class CalculatorImpl : ICalculator {
    override fun evaluateExpression(expression: String): Flowable<DisplayVM> {
        //TODO implement
        return Flowable.just(DisplayVM.createSuccessModel("4"))
    }
}