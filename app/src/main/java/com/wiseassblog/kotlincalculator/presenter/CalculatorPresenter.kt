package com.wiseassblog.kotlincalculator.presenter

import androidx.lifecycle.Observer
import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.repository.IEvaluator
import com.wiseassblog.kotlincalculator.util.DispatcherProvider
import com.wiseassblog.kotlincalculator.view.IViewContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


/**
 * Created by R_KAY on 12/20/2017.
 *
 * This right here, is D.I.
 */
private const val EMPTY = ""

class CalculatorPresenter(private var view: IViewContract.View,
                          private var viewModel: IViewContract.ViewModel,
                          private val eval: IEvaluator,
                          private val dispatcher: DispatcherProvider) :
        IViewContract.Presenter, Observer<String>, CoroutineScope {

    lateinit var jobTracker: Job

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    //From Observer Interface, to watch
    override fun onChanged(t: String?) {
        view.setDisplay(t ?: "")
    }

    //Search my channel (youtube.com/wiseAss) for an explanation of single expression syntax
    //or just pretend the = sign is a stand in for brackets {}
    override fun onLongDeleteClick() = viewModel.setDisplayState(EMPTY)

    override fun onInputButtonClick(value: String) = viewModel.setDisplayState(
            viewModel.getDisplayState() + value
    )


    override fun onDeleteClick() = viewModel.setDisplayState(
                viewModel.getDisplayState().dropLast(1)
        )

    override fun onEvaluateClick(expression: String) {
        evaluateExpression(expression)
    }

    //IMPORTANT: launch is considered as an extension function of the Presenter object!!!
    private fun evaluateExpression(expression: String) = launch {
        val result = eval.evaluateExpression(expression)

        when (result) {
            is EvaluationResult.Value -> updateViewModel(result.value)
            is EvaluationResult.Error -> showError(result.error.message.toString())
        }
    }

    private fun updateViewModel(value: String) = viewModel.setDisplayState(value)

    private fun showError(error: String) = view.showError(error)

    override fun bind() {
        jobTracker = Job()
        viewModel.setObserver(this)
    }

    override fun clear() = jobTracker.cancel()
}

