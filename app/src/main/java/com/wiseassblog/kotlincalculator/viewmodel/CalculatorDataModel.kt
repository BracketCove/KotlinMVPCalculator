package com.wiseassblog.kotlincalculator.viewmodel

/**
 * It Models Data.
 *
 * Created by R_KAY on 12/25/2017.
 */

class CalculatorDataModel private constructor(var result: String,
                                              var successful: Boolean) {

    //Since our operations will undoubtedly execute quickly, we don't need to worry about about
    //creating a 'LoadingModel'. I thought I'd mention the idea here though.
    companion object Factory{
        fun createSuccessModel(result: String): CalculatorDataModel {
            return CalculatorDataModel(result,
                    true)
        }

        fun createFailureModel(result: String): CalculatorDataModel {
            return CalculatorDataModel(result,
                    false)
        }
        //could even do createLoadingModel if appropriate
    }
}