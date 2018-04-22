package com.wiseassblog.kotlincalculator.data.datamodel

/**
 * Two ways to create fields in Kotlin:
 * Val - Read Only (similar to final)
 * Var - Mutable
 *
 * Kotlin reverses Java syntax somewhat:
 * val/var [name]:[Type/Class]
 *
 * The Parenthesis whcih follow the Class name, essentially act as a Constructor.
 * Created by R_KAY on 1/20/2018.
 */
data class ExpressionDataModel(val value:String,
                               val isValid:Boolean)