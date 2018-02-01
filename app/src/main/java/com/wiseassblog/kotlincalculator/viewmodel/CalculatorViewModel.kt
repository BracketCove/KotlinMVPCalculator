package com.wiseassblog.kotlincalculator.viewmodel

import android.arch.lifecycle.ViewModel
import com.wiseassblog.kotlincalculator.view.IViewContract
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * This thing contains the state of the View, and makes it easy for the Presenter to sort out
 * calls to the View.
 *
 * Shout-out to mon ami Darel Bitsy for suggestion of making the ViewModel's data into a Publisher
 * which Presenter can subscribe to.
 * Created by R_KAY on 1/29/2018.
 */
class CalculatorViewModel(private val dataModel: CalculatorDataModel
                          = CalculatorDataModel.createSuccessModel(""),
                          private val displayFlowable: PublishSubject<String>
                          = PublishSubject.create()) : ViewModel(),
        IViewContract.ViewModel {
    override fun getDisplayState(): String {
        return dataModel.result
    }


    //Think of this like RxJava
    override fun getDisplayStatePublisher(): Flowable<String> {
        return displayFlowable.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun setDisplayState(result: String) {
        this.dataModel.result = result
        displayFlowable.onNext(getDisplayState())
    }
}

