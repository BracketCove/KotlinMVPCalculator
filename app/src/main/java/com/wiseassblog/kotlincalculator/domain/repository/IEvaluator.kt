package com.wiseassblog.kotlincalculator.domain.repository

import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult

interface IEvaluator {
    suspend fun evaluateExpression(expression: String): EvaluationResult<Exception, String>
}