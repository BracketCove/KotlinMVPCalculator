package com.wiseassblog.kotlincalculator.data.implementations

import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.repository.IEvaluator

/**
 * In a more complex project I'd probably want to see interfaces passed in, but default arguments
 * + mocks is sufficient for my purposes.
 */
class EvaluatorImpl(private val calculator: CalculatorImpl,
                    private val validator: ValidatorImpl) : IEvaluator {

    override suspend fun evaluateExpression(expression: String): EvaluationResult<Exception, String> {
        val validationResult = validator.validateExpression(expression)

        when (validationResult) {
            is EvaluationResult.Value -> {
                return calculator.evaluateExpression(expression)
            }

            is EvaluationResult.Error -> return validationResult
        }

    }
}