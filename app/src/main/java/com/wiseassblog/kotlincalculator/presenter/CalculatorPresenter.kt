package com.wiseassblog.kotlincalculator.presenter

import android.arch.lifecycle.Observer
import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.util.DispatcherProvider
import com.wiseassblog.kotlincalculator.view.IViewContract
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by R_KAY on 12/20/2017.
 *
 * This right here, is D.I.
 */
class CalculatorPresenter(private var view: IViewContract.View,
                          private var viewModel: IViewContract.ViewModel,
                          private val eval: EvaluateExpression,
                          private val dispatcher: DispatcherProvider) :
        IViewContract.Presenter, Observer<String>, CoroutineScope {

    lateinit var jobTracker: Job

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    //From Observer Interface, to watch
    override fun onChanged(t: String?) {
        view.setDisplay(t ?: "")
    }

    private val EMPTY = ""

    /*jobTracker works like a CompositeDisposable
    *
    * Essentially, I use it as a container for all other executed coroutines, which allows me
    * to cancel them all at once.
    * */


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

    //IMPORTANT: launch is considered as an extension function of the Presenter object!!!
    private fun evaluateExpression(expression: String) = launch {
        val result = eval.execute(expression)

        when (result) {
            is EvaluationResult.Value -> {
                updateViewModel(result.value)
            }
            is EvaluationResult.Error -> {
                showError(result.error.message.toString())
            }
        }
    }

    private fun updateViewModel(value: String) = launch {
        viewModel.setDisplayState(value)
    }

    private fun showError(error: String) = launch {
        view.showError(error)
    }

    override fun bind() {
        jobTracker = Job()
        viewModel.setObserver(this)
    }

    override fun clear() {
        jobTracker.cancel()
    }

}

