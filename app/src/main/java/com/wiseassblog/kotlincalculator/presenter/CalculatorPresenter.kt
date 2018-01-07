package com.wiseassblog.kotlincalculator.presenter

import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.util.BaseSchedulerProvider
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.DisplayVM
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created by R_KAY on 12/20/2017.
 */
class CalculatorPresenter(private var view: IViewContract.View,
                          private val scheduler: BaseSchedulerProvider,
                          private val calculator: ICalculator
                         ): IViewContract.Presenter {
    override fun onEvaluateClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInputButtonClick(value: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val eventStream = CompositeDisposable()


    fun onEvaluateClick(expression: String) {
        eventStream.add(
                calculator.evaluateExpression(expression)
                        .observeOn(scheduler.getUiScheduler())
                        .subscribeWith(object: DisposableSubscriber<DisplayVM>(){
                            override fun onNext(vm: DisplayVM?) {
                                if (vm!!.successful){
                                    view.setDisplay(vm.result)
                                } else {
                                    //recoverable errors
                                    view.showError(vm.result)
                                }
                            }

                            //Reserved for fatal errors
                            override fun onError(t: Throwable?) {
                                restartFeature()
                            }

                            override fun onComplete() {
                                //un-used
                            }
                        })

        )

    }

    private fun restartFeature() {
        eventStream.clear()
        view.restartFeature()
    }

}

