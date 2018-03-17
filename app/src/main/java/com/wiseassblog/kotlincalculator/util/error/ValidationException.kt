package com.wiseassblog.kotlincalculator.util.error

/**
 * simil
 * Created by R_KAY on 3/1/2018.
 */
class ValidationException: Exception(){
    companion object {
        val message = "Invalid ExpressionDataModel"
    }
}