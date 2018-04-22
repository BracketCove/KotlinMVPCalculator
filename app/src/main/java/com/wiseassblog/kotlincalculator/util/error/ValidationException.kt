package com.wiseassblog.kotlincalculator.util.error

/**
 *
 * Created by R_KAY on 3/1/2018.
 */
class ValidationException: Exception(){
    companion object {
        const val message = "Invalid ExpressionDataModel"
    }
}