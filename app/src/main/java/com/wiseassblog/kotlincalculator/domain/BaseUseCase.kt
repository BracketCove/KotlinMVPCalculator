package com.wiseassblog.kotlincalculator.domain

import com.wiseassblog.kotlincalculator.domain.domainmodel.ExpressionResult
import io.reactivex.Flowable

/**
 * <T> stands for a generic "Type", to be decided when the class which inherets from BaseUseCase is created. See class declaration for EvaluateExpression.
 *
 * Created by R_KAY on 12/20/2017.
 */
interface BaseUseCase {
    fun execute(expression: String): Flowable<ExpressionResult<Exception, String>>
}

