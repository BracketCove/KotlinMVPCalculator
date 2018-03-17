package com.wiseassblog.kotlincalculator.domain.repository

import com.wiseassblog.kotlincalculator.data.datamodel.ExpressionDataModel
import io.reactivex.Flowable

/**
 * Created by R_KAY on 12/21/2017.
 */
interface ICalculator {

    //operates asynchronously via Rxjava
    fun evaluateExpression(expression: String): Flowable<ExpressionDataModel>
}