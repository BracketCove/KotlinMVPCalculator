package com.wiseassblog.kotlincalculator.domain.domainmodel

/**
 * This class models the data of a mathematical expression.
 *
 * @param result: Either an evaluated expression, or an error message
 * @param successful:One of: False - expression was deemed invalid. Result is an error message.
 * True - result is evaluated expression
 *
 * Created by R_KAY on 12/25/2017.
 */
class Expression private constructor(var result: String,
                                     var successful: Boolean) {
    companion object Factory{
        fun createSuccessModel(result: String): Expression {
            return Expression(result,
                    true)
        }

        fun createFailureModel(result: String): Expression {
            return Expression(result,
                    false)
        }
        //could even do createLoadingModel if appropriate. This App is simple enough that it doesn't need long running operations
    }
}