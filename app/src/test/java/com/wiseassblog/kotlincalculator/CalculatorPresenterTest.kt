package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.repository.IEvaluator
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.util.DispatcherProvider
import com.wiseassblog.kotlincalculator.util.VALIDATION_ERROR
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.view.Inputs
import com.wiseassblog.kotlincalculator.view.ViewEvent
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

    //first we set up our Mock objects
    private val view: IViewContract.View = mockk(relaxed = true)
    private val viewModel: IViewContract.ViewModel = mockk(relaxed = true)
    private val eval: IEvaluator = mockk()
    private val dispatcher: DispatcherProvider = mockk()

    val presenter: CalculatorPresenter = CalculatorPresenter(view, viewModel, eval, dispatcher)

    @BeforeEach
    fun setUpMocks(){
        //clear mock interactions
        clearAllMocks()

        //mockk response functions
        every { dispatcher.provideUIContext() } returns Dispatchers.Unconfined
    }

    /**
     * When on start is called, bind the view listeners to the presenter and set observer for ViewModel.
     */
    @Test
    fun `On Start`(){

        //This is the Unit I would like to test
        presenter.onEvent(ViewEvent.OnStart)

        //verify checks to see if this particular function was called, on this mock
        verify { view.bindEventListener() }
        verify { viewModel.setObserver(presenter) }
    }

    /**
     * When on start is called, bind the view listeners to the presenter and set observer for ViewModel.
     */
    @Test
    fun `On Changed`(){
        val TEST_ANSWER = "4.0"
        //This is the Unit I would like to test
        presenter.onChanged(TEST_ANSWER)

        //verify checks to see if this particular function was called, on this mock
        verify { view.setDisplay(TEST_ANSWER) }
    }


    /**
     *1. We want to grab the current expression within the viewModel
     *2. Give that data to the eval repository
     *3. Display appropriate result: one of
     * a. Expression was valid, display result of calculuating that Expression
     * b. Expression was invalid, display toast
     */
    @Test
    fun `On Evaluate Click Valid Expression`() = runBlocking {

        val TEST_VALID_EXPRESSION = "2 + 2"
        val TEST_VALID_ANSWER = "4.0"

        every { viewModel.getDisplayState() } returns TEST_VALID_EXPRESSION

        coEvery { eval.evaluateExpression(TEST_VALID_EXPRESSION) } returns EvaluationResult.build { TEST_VALID_ANSWER }


        //This is the Unit I would like to test
        presenter.onEvent(ViewEvent.OnEvaluateClick)

        //verify checks to see if this particular function was called, on this mock
        verify { viewModel.setDisplayState(TEST_VALID_ANSWER) }
        coVerify { eval.evaluateExpression(TEST_VALID_EXPRESSION) }
        verify { viewModel.getDisplayState() }
    }


    /**
     * Delete the last character to be added from viewModel, and subsequently the View
     */
    @Test
    fun `On Delete Click Not Empty`(){
        val TEST_ORIGINAL = "2+2"
        val TEST_RESULT = "2+"

        every { viewModel.getDisplayState() } returns TEST_ORIGINAL
        //This is the Unit I would like to test
        presenter.onEvent(ViewEvent.OnDeleteClick)

        //verify checks to see if this particular function was called, on this mock
        verify { viewModel.getDisplayState() }
        verify { viewModel.setDisplayState(TEST_RESULT) }
    }

    /**
     * Delete the last character to be added from viewModel, and subsequently the View
     */
    @Test
    fun `On Delete Click Empty`(){
        val TEST_ORIGINAL = ""

        every { viewModel.getDisplayState() } returns TEST_ORIGINAL
        //This is the Unit I would like to test
        presenter.onEvent(ViewEvent.OnDeleteClick)

        //verify checks to see if this particular function was called, on this mock
        verify { viewModel.getDisplayState() }
        verify {  view wasNot Called }
    }

    /**
     * Completely clear the viewModel, and subsequently the View display
     */
    @Test
    fun `On Long Delete Click`(){

        presenter.onEvent(ViewEvent.OnLongDeleteClick)

        verify { viewModel.setDisplayState("") }
    }

    /**
     * When on start is called, bind the view listeners to the presenter and set observer for ViewModel.
     */
    @Test
    fun `On Number Click 1`(){
        every { viewModel.getDisplayState() } returns ""

        //This is the Unit I would like to test
        presenter.onEvent(ViewEvent.OnOperandClick(Inputs.ONE))


        verify { viewModel.setDisplayState(Inputs.ONE.value.toString()) }
        verify { viewModel.getDisplayState() }
    }

    /**
     * Don't allow user to add an operator to an empty string (this could be handled in the backend,
     * but it's an obvious broken expression
     */
    @Test
    fun `On Operator Click Empty`(){
        every { viewModel.getDisplayState() } returns ""

        presenter.onEvent(ViewEvent.OnOperatorClick(Inputs.MULTIPLY))

        verify { viewModel.getDisplayState() }
        verify { view.showError(VALIDATION_ERROR) }
    }

    /**
     * When an operator button is clicked, append it to the current viewModel data and view
     */
    @Test
    fun `On Operator Click Not Empty`(){
        val TEST_CURRENT_STATE = "2"
        val TEST_RESULT_STATE = "2*"

        every { viewModel.getDisplayState() } returns TEST_CURRENT_STATE

        presenter.onEvent(ViewEvent.OnOperatorClick(Inputs.MULTIPLY))

        verify { viewModel.setDisplayState(TEST_RESULT_STATE) }
        verify { viewModel.getDisplayState() }
    }
}















