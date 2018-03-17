package com.wiseassblog.kotlincalculator.domain.usecase

import com.wiseassblog.kotlincalculator.domain.BaseUseCase
import com.wiseassblog.kotlincalculator.domain.domainmodel.Expression
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.domain.repository.IValidator
import com.wiseassblog.kotlincalculator.util.error.ValidationException
import com.wiseassblog.kotlincalculator.util.scheduler.BaseSchedulerProvider
import io.reactivex.Flowable


/**
 * Created by R_KAY on 12/20/2017.
 */
class EvaluateExpression(private val calculator: ICalculator,
                         private val validator: IValidator,
                         private val scheduler: BaseSchedulerProvider) : BaseUseCase<Expression> {

    override fun execute(expression: String): Flowable<Expression> {
        //Validator operates synchronously because having to much Rx stuff here seems to freak
        //people out. Also, it really doesn't need to be thread.
        if (validator.validateExpression(expression)) {
            return calculator.evaluateExpression(expression)
                    //Note: result is something I declare, but it's type comes from the type
                    //which we are observing, which is ExpressionDataModel (see return type of ICalculator.kt)
                    .flatMap { result ->
                        Flowable.just(
                                Expression.createSuccessModel(result.value)
                        )
                    }
                    .subscribeOn(scheduler.getComputationScheduler())
        }

        return Flowable.just(
                Expression.createFailureModel(ValidationException.message)
        )
    }
}
