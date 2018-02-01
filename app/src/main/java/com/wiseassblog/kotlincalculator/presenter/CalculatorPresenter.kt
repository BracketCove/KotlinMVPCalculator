package com.wiseassblog.kotlincalculator.presenter

import android.arch.lifecycle.LifecycleObserver
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.util.BaseSchedulerProvider
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorDataModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by R_KAY on 12/20/2017.
 */
class CalculatorPresenter(private var view: IViewContract.View,
                          private var viewModel: IViewContract.ViewModel,
                          private val scheduler: BaseSchedulerProvider,
                          private val eval: EvaluateExpression) : IViewContract.Presenter {

    private val eventStream = CompositeDisposable()

    private val EMPTY = ""

    //Update the state, then the view.
    override fun onLongDeleteClick() {
        viewModel.setDisplayState(EMPTY)
    }

    override fun onInputButtonClick(value: String) {
        viewModel.setDisplayState(
                viewModel.getDisplayState() + value
        )
    }

    override fun onDeleteClick() {
        viewModel.setDisplayState(
                viewModel.getDisplayState().dropLast(1)
        )
    }

    override fun onEvaluateClick(expression: String) {
        eventStream.add(
                eval.execute(expression)
                        .observeOn(scheduler.getUiScheduler())
                        .subscribeWith(object : DisposableSubscriber<CalculatorDataModel>() {
                            override fun onNext(dataModel: CalculatorDataModel?) {
                                if (dataModel!!.successful) {
                                    viewModel.setDisplayState(dataModel.result)
                                } else {
                                    view.showError(dataModel.result)
                                }
                            }

                            //Reserved for fatal errors
                            override fun onError(t: Throwable?) {
                                restartFeature()
                            }

                            override fun onComplete() {//huehuehuehuehuehuehuehuehue
                            }
                        })
        )
    }

    private fun restartFeature() {
        eventStream.clear()
        view.restartFeature()
    }

    override fun bind() {
        eventStream.add(
                viewModel.getDisplayStatePublisher()
                        .subscribeWith(
                                object : DisposableSubscriber<String>() {
                                    override fun onNext(displayState: String) {
                                        view.setDisplay(displayState)
                                    }

                                    override fun onError(t: Throwable?) {
                                        restartFeature()
                                    }

                                    override fun onComplete() {}
                                }
                        )
        )
        //  view.setDisplay(viewModel.getDisplayStateFlowable())
    }

    override fun clear() {
        eventStream.clear()
    }

}

