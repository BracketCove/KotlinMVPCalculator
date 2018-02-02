package com.wiseassblog.kotlincalculator.viewmodel

/**
 * It Models the Data of an Expression (a mathematical Expression, that is.
 *
 * Created by R_KAY on 12/25/2017.
 */

class ExpressionDataModel private constructor(var result: String,
                                              var successful: Boolean) {

    //Since our operations will undoubtedly execute quickly, we don't need to worry about about
    //creating a 'LoadingModel'. I thought I'd mention the idea here though.
    companion object Factory{
        fun createSuccessModel(result: String): ExpressionDataModel {
            return ExpressionDataModel(result,
                    true)
        }

        fun createFailureModel(result: String): ExpressionDataModel {
            return ExpressionDataModel(result,
                    false)
        }
        //could even do createLoadingModel if appropriate
    }
}