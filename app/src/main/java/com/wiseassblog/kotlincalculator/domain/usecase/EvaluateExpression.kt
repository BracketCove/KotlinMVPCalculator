package com.wiseassblog.kotlincalculator.domain.usecase

import com.wiseassblog.kotlincalculator.data.INVALID_EXPRESSION
import com.wiseassblog.kotlincalculator.domain.BaseUseCase
import com.wiseassblog.kotlincalculator.domain.domainmodel.ExpressionResult
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.domain.repository.IValidator


/**
 * Created by R_KAY on 12/20/2017.
 */
class EvaluateExpression(private val calculator: ICalculator,
                         private val validator: IValidator) : BaseUseCase {

    override suspend fun execute(expression: String): ExpressionResult<Exception, String> {

        val validationResult = validator.validateExpression(expression)

        return when (validationResult) {
            is ExpressionResult.Value ->
                //expression was valid, send to calculator
                calculator.evaluateExpression(expression)
                //expression invalid
            is ExpressionResult.Error -> ExpressionResult.build {
                throw Exception(INVALID_EXPRESSION)
            }
        }
//
//        return when (validationResult) {
//        //valid input, proceed to calculation
//            is ExpressionResult.Value -> Flowable.fromCallable {
//                calculator.evaluateExpression(expression)
//            }.subscribeOn(scheduler.getComputationScheduler())
//        //invalid input
//            is ExpressionResult.Error -> Flowable.just(
//                    validationResult
//            )
//        }
    }
}
