package com.wiseassblog.kotlincalculator.presenter

import android.arch.lifecycle.LifecycleObserver
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.util.BaseSchedulerProvider
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorUIModel
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by R_KAY on 12/20/2017.
 */
class CalculatorPresenter(private var view: IViewContract.View,
                          private var viewModel: CalculatorViewModel,
                          private val scheduler: BaseSchedulerProvider,
                          private val eval: EvaluateExpression) : IViewContract.Presenter,
                                                                  LifecycleObserver  {

    private val eventStream = CompositeDisposable()

    //Update the state, then the view.
    override fun onLongDeleteClick() {
        viewModel.setDisplayState(CalculatorUIModel.createSuccessModel(""))
        view.setDisplay(viewModel.getsDisplayState())
    }

    override fun onInputButtonClick(value: String) {
        viewModel.setDisplayState(
                CalculatorUIModel.createSuccessModel(viewModel.getsDisplayState() + value)
        )
        view.setDisplay(viewModel.uiModel.result)
    }

    override fun onDeleteClick() {
        viewModel.setDisplayState(
                CalculatorUIModel.createSuccessModel(viewModel.getsDisplayState().dropLast(1))
        )
        view.setDisplay(viewModel.uiModel.result)
    }

    //Strongly considering trying coroutines here; could make another branch.
    override fun onEvaluateClick(expression: String) {
        eventStream.add(
                eval.execute(expression)
                        .observeOn(scheduler.getUiScheduler())
                        .subscribeWith(object : DisposableSubscriber<CalculatorUIModel>() {
                            override fun onNext(uiModel: CalculatorUIModel?) {
                                if (uiModel!!.successful) {
                                    viewModel.setDisplayState(uiModel)
                                    view.setDisplay(viewModel.getsDisplayState())
                                } else {
                                    view.showError(uiModel.result)
                                }
                            }

                            //Reserved for fatal errors
                            override fun onError(t: Throwable?) {
                                restartFeature()
                            }

                            override fun onComplete() {
                                //huehuehuehuehuehuehuehuehue
                            }
                        })
        )
    }

    private fun restartFeature() {
        eventStream.clear()
        view.restartFeature()
    }

    override fun bind() {
        view.setDisplay(viewModel.getsDisplayState())
    }

    override fun clear() {
        eventStream.clear()
    }

}

