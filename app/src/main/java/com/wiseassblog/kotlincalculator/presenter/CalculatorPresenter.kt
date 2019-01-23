package com.wiseassblog.kotlincalculator.presenter

import androidx.lifecycle.Observer
import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.repository.IEvaluator
import com.wiseassblog.kotlincalculator.util.DispatcherProvider
import com.wiseassblog.kotlincalculator.util.VALIDATION_ERROR
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.view.Inputs
import com.wiseassblog.kotlincalculator.view.ViewEvent
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


    init {
        jobTracker = Job()
    }

    //This will act as a dispatcher function
    override fun onEvent(event: ViewEvent<Inputs>) {
        when (event) {
            ViewEvent.OnStart -> onStart()
            ViewEvent.OnEvaluateClick -> onEvaluateClick()
            ViewEvent.OnDeleteClick -> onDeleteClick()
            ViewEvent.OnLongDeleteClick -> onLongDeleteClick()
            is ViewEvent.OnOperandClick<Inputs> -> onOperandClick(event.char)
            is ViewEvent.OnOperatorClick<Inputs> -> onOperatorClick(event.char)
            ViewEvent.OnDestroy -> onDestroy()
        }
    }

    private fun onDestroy() = jobTracker.cancel()

    private fun onOperatorClick(char: Inputs) {
        val state = viewModel.getDisplayState()
        if (state != "") viewModel.setDisplayState( state + char.value )
        else view.showError(VALIDATION_ERROR)
    }

    private fun onOperandClick(char: Inputs) = viewModel.setDisplayState(viewModel.getDisplayState() + char.value)

    private fun onLongDeleteClick() = viewModel.setDisplayState("")


    private fun onDeleteClick() {
        val state = viewModel.getDisplayState()
        if (state != "") viewModel.setDisplayState( state.dropLast(1) )
    }

    private fun onEvaluateClick() = launch {

        val result = eval.evaluateExpression(viewModel.getDisplayState())

        when (result) {
            is EvaluationResult.Value -> viewModel.setDisplayState(result.value)
            is EvaluationResult.Error -> view.showError(VALIDATION_ERROR)
        }
    }

    private fun onStart() {
        view.bindEventListener()
        viewModel.setObserver(this)
    }

    override fun onChanged(t: String?) {
        view.setDisplay(t ?: "")
    }


    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker


}

