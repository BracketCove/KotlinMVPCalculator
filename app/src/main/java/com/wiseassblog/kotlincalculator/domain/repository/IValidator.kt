package com.wiseassblog.kotlincalculator.domain.repository

import com.wiseassblog.kotlincalculator.data.datamodel.Expression
import io.reactivex.Flowable

/**
 * Created by R_KAY on 1/20/2018.
 */
public interface IValidator {
    //return unaltered expression or error message
    fun validateExpression(expression:String):Flowable<Expression>
}