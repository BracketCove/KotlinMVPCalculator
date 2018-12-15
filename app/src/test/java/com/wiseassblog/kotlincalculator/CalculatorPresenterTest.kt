package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.repository.IEvaluator
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.util.DispatcherProvider
import com.wiseassblog.kotlincalculator.util.EvaluationError
import com.wiseassblog.kotlincalculator.util.VALIDATION_ERROR
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test behaviour of Presenter.
 *
 */
class CalculatorPresenterTest {

    private val view: IViewContract.View = mockk(relaxed = true)

    private val dispatcher: DispatcherProvider = mockk()

    private val viewModel: CalculatorViewModel = mockk(relaxed = true)

    private val eval: IEvaluator = mockk()

    val presenter = CalculatorPresenter(view, viewModel, eval, dispatcher)

    val EXPRESSION = "2+2"
    val ANSWER = "4"

    val INVALID_EXPRESSION = "2+Q"


    @BeforeEach
    fun setUpRedundantMocks() {
        clearMocks()
        every { dispatcher.provideUIContext() } returns Dispatchers.Unconfined
        presenter.bind()

    }

    /**
     * User hits evaluate, expression is valid. Should return mathematically accurate evaluation
     * of the input as a String.
     */
    @Test
    fun onEvaluateValidSimpleExpression() = runBlocking {

        coEvery { eval.evaluateExpression(EXPRESSION) } returns EvaluationResult.build { ANSWER }

        //this is the "Unit" what we are testing
        presenter.onEvaluateClick(EXPRESSION)

        //These are the assertions which must be satisfied in order to pass the test
        coVerify { eval.evaluateExpression(EXPRESSION) }
        coVerify { viewModel.setDisplayState(ANSWER) }
    }

    @Test
    fun onEvaluateInvalidExpression() = runBlocking {
        coEvery { eval.evaluateExpression(INVALID_EXPRESSION) } returns EvaluationResult.build { throw EvaluationError.ValidationError() }

        presenter.onEvaluateClick(INVALID_EXPRESSION)

        //These are the assertions which must be satisfied in order to pass the test
        coVerify { eval.evaluateExpression(INVALID_EXPRESSION) }
        coVerify { view.showError(VALIDATION_ERROR) }
    }
}
