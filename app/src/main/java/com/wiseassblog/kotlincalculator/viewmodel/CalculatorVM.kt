package com.wiseassblog.kotlincalculator.viewmodel

/**
 * This ViewModel represents potential states of the UI which Display Fragment can render. This
 * includes:
 * Error state - Expression was determined to be invalid. Return error message.
 * Success state - Expression was determined to be valid. Return evaluated Expression
 *
 * The MVVM GUI Architecture Pattern (a.k.a. Presentation Model à la Martin Fowler) is a different concept; but I believe this is a more
 * appropriate usage of the word, imho.
 * ,
 * Created by R_KAY on 12/25/2017.
 */

class CalculatorVM private constructor(val result: String,
                                    val successful: Boolean) {

    //Since our operations will undoubtedly execute quickly, we don't need to worry about about
    //creating a 'LoadingModel'. I thought I'd mention the idea here though.
    companion object Factory{
        fun createSuccessModel(result: String): CalculatorVM{
            return CalculatorVM(result,
                    true)
        }

        fun createFailureModel(result: String): CalculatorVM{
            return CalculatorVM(result,
                    false)
        }
        //could even do createLoadingModel if appropriate
    }
}