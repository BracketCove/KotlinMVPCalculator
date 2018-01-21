package com.wiseassblog.kotlincalculator.presenter

import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.util.BaseSchedulerProvider
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorVM
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by R_KAY on 12/20/2017.
 */
class CalculatorPresenter(private var view: IViewContract.View,
                          private val scheduler: BaseSchedulerProvider,
                          private val eval:EvaluateExpression
                         ): IViewContract.Presenter {

    private val eventStream = CompositeDisposable()

    override fun onInputButtonClick(value: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeleteClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onEvaluateClick() {
        eventStream.add(
                eval.execute(view.getCurrentExpression())
                        .observeOn(scheduler.getUiScheduler())
                        .subscribeWith(object: DisposableSubscriber<CalculatorVM>(){
                            override fun onNext(vm: CalculatorVM?) {
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
                                //huehuehuehuehuehuehuehuehue
                            }
                        })
        )
    }

    private fun restartFeature() {
        eventStream.clear()
        view.restartFeature()
    }

}

