package com.wiseassblog.kotlincalculator.domain.repository

import com.wiseassblog.kotlincalculator.data.datamodel.Expression
import io.reactivex.Flowable

/**
 * Created by R_KAY on 12/21/2017.
 */
interface ICalculator {

    fun evaluateExpression(expression: String): Flowable<Expression>
}