package com.wiseassblog.kotlincalculator.data

import com.wiseassblog.kotlincalculator.data.datamodel.Expression
import com.wiseassblog.kotlincalculator.domain.repository.IValidator
import io.reactivex.Flowable

/**
 * Created by R_KAY on 1/20/2018.
 */
class ValidatorImpl : IValidator {
    override fun validateExpression(expression: String): Flowable<Expression> {
        when {
            expression.startsWith("+") -> Flowable.just(Expression("Invalid Expression.", false))
            expression.startsWith("-") -> Flowable.just(Expression("Invalid Expression.", false))
            expression.startsWith("*") -> Flowable.just(Expression("Invalid Expression.", false))
            expression.startsWith("/") -> Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("+") -> Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("-") -> Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("*") -> Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("/") -> Flowable.just(Expression("Invalid Expression.", false))
        }

        expression.indices
                .filter {
                    it == expression.length
                }
                .forEach { concurrentOperators(expression[it], expression[it + 1]) }

        return Flowable.just(Expression(expression, true))
    }

    private fun concurrentOperators(current: Char, next: Char): Boolean {
        if (isOperator(current) && isOperator(next)){
            return true
        }

        return false
    }

    private fun isOperator(char: Char):Boolean{
        return when {
            char.equals("+") -> true
            char.equals("-") -> true
            char.equals("*") -> true
            char.equals("/") -> true
            else -> false
        }
    }

}