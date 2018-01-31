package com.wiseassblog.kotlincalculator.viewmodel

import android.arch.lifecycle.ViewModel
import com.wiseassblog.kotlincalculator.view.IViewContract
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Wondering is with UI Model and ViewModel? Well, I'm really not the expert here (not sarcasm),
 * however I would actually prefer to call the thing which I pass around my Presentation Layer as a
 * a ViewModel (I'm referring to what is currently called CalculatorUIModel). I would actually call
 * this thing a ViewController, but that probably would've confused people even worse.
 *
 * Let's try to forget about the names (the concepts are what matter) and focus on the goal of
 * separation of concerns and immutability.
 * Created by R_KAY on 1/29/2018.
 */
class CalculatorViewModel(var uiModel: CalculatorUIModel
                          = CalculatorUIModel.createSuccessModel("")) : ViewModel(),
        IViewContract.ViewModel {

    override fun getsDisplayState(): String {
        return uiModel.result
    }

    override fun setDisplayState(uiModel: CalculatorUIModel) {
        this.uiModel = uiModel
    }
}

