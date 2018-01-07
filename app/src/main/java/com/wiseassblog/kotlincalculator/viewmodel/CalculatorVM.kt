package com.wiseassblog.kotlincalculator.viewmodel

/**
 * This ViewModel represents potential states of the UI which Display Fragment can render. This
 * includes:
 * Error state - Expression was determined to be invalid. Return error message.
 * Success state - Expression was determined to be valid. Return evaluated Expression
 * Created by R_KAY on 12/25/2017.
 */

class DisplayVM private constructor(val result: String,
                                    val successful: Boolean) {

    companion object Factory{
        fun createSuccessModel(result: String): DisplayVM{
            return DisplayVM(result,
                    true)
        }

        fun createFailureModel(result: String): DisplayVM{
            return DisplayVM(result,
                    false)
        }

        //could even do createLoadingModel if appropriate
    }
}