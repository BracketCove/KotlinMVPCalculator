package com.wiseassblog.kotlincalculator.domain.domainmodel

import com.wiseassblog.kotlincalculator.util.EvaluationError
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

//    fun <V> value(value: V): EvaluationResult<Nothing, V> = Value(value)
//    fun <E> error(msg: E): EvaluationResult<E, Nothing> = Error(msg)

    companion object {
        inline fun <V> build(function: () -> V): EvaluationResult<Exception, V> =
                try {
                    Value(function.invoke())
                } catch (e: Exception) {
                    Error(e)
                }
    }

}