package com.wiseassblog.kotlincalculator.domain.domainmodel

import java.lang.Exception

/**
 *
 * Either Monad:
 * Either<E, V>
 *     E: Error
 *     V: Value
 *
 * By convention, E is always on the left
 * V is on the Right
 *
 * Either<Left, Right>
 *
 *
 * Created by R_KAY on 12/25/2017.
 */
sealed class EvaluationResult<out E, out V> {

    //two specified sub-types
    data class Value<out V>(val value: V) : EvaluationResult<Nothing, V>()
    data class Error<out E>(val error: E) : EvaluationResult<E, Nothing>()

    companion object Factory{
        inline fun <V> buildValue(function: () -> V): EvaluationResult<Nothing, V> {
            return Value(function.invoke())
        }

        inline fun buildError(error: Exception): EvaluationResult<Exception, Nothing> {
            return Error(error)
        }
    }
}