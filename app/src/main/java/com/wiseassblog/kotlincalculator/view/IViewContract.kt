package com.wiseassblog.kotlincalculator.view

import com.wiseassblog.kotlincalculator.viewmodel.CalculatorUIModel

/**
 * Created by R_KAY on 10/8/2017.
 */
interface IViewContract {

    interface View {
        fun getCurrentExpression(): String
        fun setDisplay(value: String)
        fun showError(value: String)
        fun restartFeature()
    }

    interface ViewModel {
       fun setDisplayState(uiModel: CalculatorUIModel)
       fun getsDisplayState():String
    }

    interface Presenter {
        fun onEvaluateClick(expression:String)
        fun onInputButtonClick(value: String)
        fun onDeleteClick()
        fun onLongDeleteClick()
        fun bind()
        fun clear()
    }
}

