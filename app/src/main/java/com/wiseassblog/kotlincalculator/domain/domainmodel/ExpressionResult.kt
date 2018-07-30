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
sealed class ExpressionResult<out E, out V> {

    //two specified sub-types
    data class Value<out V>(val value: V) : ExpressionResult<Nothing, V>()
    data class Error<out E>(val error: E) : ExpressionResult<E, Nothing>()

    companion object Factory{
        inline fun <V> build(function: () -> V): ExpressionResult<Exception, V> {
            return try {
                //invoke calls the passed in function
                Value(function.invoke())
            } catch (e: Exception) {
                Error(e)
            }
        }
    }
}