package com.wiseassblog.kotlincalculator.domain.repository

import com.wiseassblog.kotlincalculator.domain.domainmodel.ExpressionResult

/**
 * Created by R_KAY on 12/21/2017.
 */
interface ICalculator {

    //operates asynchronously via Rxjava
    suspend fun evaluateExpression(expression: String): ExpressionResult<Exception, String>
}