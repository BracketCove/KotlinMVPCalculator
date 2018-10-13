package com.wiseassblog.kotlincalculator.domain

import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult

/**
 * <T> stands for a generic "Type", to be decided when the class which inherets from BaseUseCase is created. See class declaration for EvaluateExpression.
 *
 * Created by R_KAY on 12/20/2017.
 */
interface BaseUseCase<E, V> {
   suspend fun execute(expression: String): EvaluationResult<E, V>
}

