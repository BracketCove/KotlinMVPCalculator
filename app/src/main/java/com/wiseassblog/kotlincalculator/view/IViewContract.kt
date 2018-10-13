package com.wiseassblog.kotlincalculator.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

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
        fun setDisplayState(result: String)

        fun setObserver(obs: Observer<String>)

        fun getDisplayState(): String
    }

    interface Presenter {
        fun onEvaluateClick(expression: String)
        fun onInputButtonClick(value: String)
        fun onDeleteClick()
        fun onLongDeleteClick()
        fun bind()
        fun clear()
    }
}

