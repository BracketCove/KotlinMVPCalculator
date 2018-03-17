package com.wiseassblog.kotlincalculator.presenter

import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.util.scheduler.BaseSchedulerProvider
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.domain.domainmodel.Expression
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
        //Presenter is the Observer
        eventStream.add(
                eval.execute(expression)
                        .observeOn(scheduler.getUiScheduler())
                        .subscribeWith(object : DisposableSubscriber<Expression>() {
                            override fun onNext(data: Expression?) {
                                if (data!!.successful) {
                                    viewModel.setDisplayState(data.result)
                                } else {
                                    view.showError(data.result)
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
                //Darel's suggestion was to make publisher
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

