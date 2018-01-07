package com.wiseassblog.kotlincalculator.view

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

    interface Presenter {
        fun onEvaluateClick()
        fun onInputButtonClick(value: String)
        fun onDeleteClick()
    }
}

