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

    //I do add this in the tutorial, but forgot about it initially
    init {
        jobTracker = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker


}

