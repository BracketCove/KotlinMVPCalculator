package com.wiseassblog.kotlincalculator.presenter

import android.arch.lifecycle.Observer
import com.wiseassblog.kotlincalculator.domain.domainmodel.ExpressionResult
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.view.IViewContract
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * Created by R_KAY on 12/20/2017.
 */
class CalculatorPresenter(private var view: IViewContract.View,
                          private var viewModel: IViewContract.ViewModel,
                          private val eval: EvaluateExpression) :
        IViewContract.Presenter, Observer<String> {
    override fun onChanged(t: String?) {
        view.setDisplay(t ?: "")
    }

    private val EMPTY = ""

    /*jobTracker works like a CompositeDisposable
    *
    * Essentially, I use it as a container for all other executed coroutines, which allows me
    * to cancel them all at once.
    * */
    private val jobTracker = Job()

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
        evaluateExpression(expression)
    }

    private fun evaluateExpression(expression: String) = launch(jobTracker) {
        val result = eval.execute(expression)

        when (result) {
            is ExpressionResult.Value -> {
                updateViewModel(result.value)
            }
            is ExpressionResult.Error -> {
                view.showError(result.error.message.toString())
            }
        }
    }

    private fun updateViewModel(value: String) = launch(UI, CoroutineStart.DEFAULT, jobTracker) {
        viewModel.setDisplayState(value)
    }

    override fun bind() {
        viewModel.setObserver(this)
    }

    override fun clear() {
        jobTracker.cancel()
    }

}

