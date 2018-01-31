package com.wiseassblog.kotlincalculator.data

import com.wiseassblog.kotlincalculator.data.datamodel.Expression
import com.wiseassblog.kotlincalculator.domain.repository.IValidator
import io.reactivex.Flowable

/**
 * Created by R_KAY on 1/20/2018.
 */
object ValidatorImpl : IValidator {
    override fun validateExpression(expression: String): Flowable<Expression> {
        when {
            expression.startsWith("+") -> return Flowable.just(Expression("Invalid Expression.", false))
            expression.startsWith("-") -> return Flowable.just(Expression("Invalid Expression.", false))
            expression.startsWith("*") -> return Flowable.just(Expression("Invalid Expression.", false))
            expression.startsWith("/") -> return Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("+") -> return Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("-") -> return Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("*") -> return Flowable.just(Expression("Invalid Expression.", false))
            expression.endsWith("/") -> return Flowable.just(Expression("Invalid Expression.", false))
        }

        expression.indices
                .forEach {
                    if (it < expression.lastIndex){
                        if (concurrentOperators(expression[it], expression[it + 1])) {
                            return Flowable.just(Expression("Invalid Expression.", false))
                        }
                    }

                }


        return Flowable.just(Expression(expression, true))
    }

    private fun concurrentOperators(current: Char, next: Char): Boolean {
        if (isOperator(current) && isOperator(next)) {
            return true
        }
        return false
    }

    private fun isOperator(char: Char): Boolean {
        return when {
            //not sure why I had to toString() but char.equals("+") was not working as expected
            char.toString() == "+" -> true
            char.toString() == "-" -> true
            char.toString() == "*" -> true
            char.toString() == "/" -> true
            else -> false
        }
    }

}