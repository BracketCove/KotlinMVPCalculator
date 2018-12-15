package com.wiseassblog.kotlincalculator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.wiseassblog.kotlincalculator.view.IViewContract

/**
 * This thing contains the state of the View, and makes it easy for the Presenter to sort out calls to the View.
 *
 * Shout-out to mon ami Darel Bitsy for suggestion of making the ViewModel's data into a Publisher which Presenter can subscribe to.
 * Created by R_KAY on 1/29/2018.
 */
class CalculatorViewModel(private var displayState: MutableLiveData<String> = MutableLiveData()) : ViewModel(),
        IViewContract.ViewModel {
    override fun getDisplayState(): String {
        //return current display state or empty string if value is null
        //see "Elvis Operator"
        return displayState.value ?: ""
    }

    //Observer is another word for Subject.
    override fun setObserver(obs: Observer<String>) {
        displayState.observeForever(obs)
    }

    override fun setDisplayState(result: String) {
        displayState.value = result
    }
}

