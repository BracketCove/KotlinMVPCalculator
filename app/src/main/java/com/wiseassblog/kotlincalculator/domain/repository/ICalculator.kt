package com.wiseassblog.kotlincalculator.domain.repository

import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.util.EvaluationError

/**
 * Created by R_KAY on 12/21/2017.
 */
interface ICalculator {

    suspend fun evaluateExpression(expression: String): EvaluationResult<Exception, String>
}