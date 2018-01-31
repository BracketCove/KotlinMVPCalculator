package com.wiseassblog.kotlincalculator.viewmodel

/**
 *
 * Wondering is with UI Model and ViewModel? Well, I'm really not the expert here (not sarcasm),
 * however I would actually prefer to call the thing which I pass around my Presentation Layer as a
 * a ViewModel (I'm referring to what is currently called CalculatorUIModel). I would actually call
 * this thing a ViewController, but that probably would've confused people even worse.
 *
 * Let's try to forget about the names and accept that the goal is separation of concerns and
 * immutability.
 *
 * This UIModel represents potential states of the UI which is to be rendered to Display Fragment. This
 * includes:
 * Error state - Expression was determined to be invalid. Return error message.
 * Success state - Expression was determined to be valid. Return evaluated Expression
 *
 * The MVVM GUI Architecture Pattern (a.k.a. Presentation Model à la Martin Fowler) is a different concept; but I believe this is a more
 * appropriate usage of the word, imho.
 * ,
 * Created by R_KAY on 12/25/2017.
 */

class CalculatorUIModel private constructor(val result: String,
                                            val successful: Boolean) {

    //Since our operations will undoubtedly execute quickly, we don't need to worry about about
    //creating a 'LoadingModel'. I thought I'd mention the idea here though.
    companion object Factory{
        fun createSuccessModel(result: String): CalculatorUIModel {
            return CalculatorUIModel(result,
                    true)
        }

        fun createFailureModel(result: String): CalculatorUIModel {
            return CalculatorUIModel(result,
                    false)
        }
        //could even do createLoadingModel if appropriate
    }
}