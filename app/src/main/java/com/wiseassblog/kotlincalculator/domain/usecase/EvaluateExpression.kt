package com.wiseassblog.kotlincalculator.domain.usecase

import com.wiseassblog.kotlincalculator.domain.BaseUseCase
import com.wiseassblog.kotlincalculator.domain.domainmodel.ExpressionResult
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.domain.repository.IValidator
import com.wiseassblog.kotlincalculator.util.scheduler.BaseSchedulerProvider
import io.reactivex.Flowable


/**
 * Created by R_KAY on 12/20/2017.
 */
class EvaluateExpression(private val calculator: ICalculator,
                         private val validator: IValidator,
                         private val scheduler: BaseSchedulerProvider) : BaseUseCase {

    override fun execute(expression: String): Flowable<ExpressionResult<Exception, String>> {
        val validationResult = validator.validateExpression(expression)

        return when (validationResult) {
        //valid input, proceed to calculation
            is ExpressionResult.Value -> Flowable.fromCallable {
                calculator.evaluateExpression(expression)
            }.subscribeOn(scheduler.getComputationScheduler())
        //invalid input
            is ExpressionResult.Error -> Flowable.just(
                    validationResult
            )
        }
    }
}
