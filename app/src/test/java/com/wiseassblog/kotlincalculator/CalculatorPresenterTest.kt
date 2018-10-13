package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.util.DispatcherProvider
import com.wiseassblog.kotlincalculator.util.EvaluationError
import com.wiseassblog.kotlincalculator.util.VALIDATION_ERROR
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorViewModel
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Test behaviour of Presenter.
 *
 * Props to Antonio Leiva for explaining how to add org.mockito.plugins.MockMaker file to
 * test/resources/mockito-extensions to enable Mocking of Kotlin final classes.
 *
 * https://antonioleiva.com/mockito-2-kotlin/
 *
 */
class CalculatorPresenterTest {

    private lateinit var presenter: CalculatorPresenter

    @Mock
    private lateinit var view: IViewContract.View

    @Mock
    private lateinit var dispatcher: DispatcherProvider

    @Mock
    private lateinit var viewModel: CalculatorViewModel

    //Although I personally prefer the term "Data" instead of "Model" to refer to an Architectural
    //Layer responsible for Data Management and Manipulation, you can think of calculator as the
    //"Model"; in a more classic sense of MVP
    @Mock
    private lateinit var eval: EvaluateExpression


    val EXPRESSION = "2+2"
    val ANSWER = "4"

    val INVALID_EXPRESSION = "2+Q"




    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = CalculatorPresenter(view, viewModel, eval, dispatcher)
        presenter.bind()

        Mockito.`when`(dispatcher.provideUIContext())
                .thenReturn(
                        Dispatchers.Unconfined
                )
    }

    /**
     * User hits evaluate, expression is valid. Should return mathematically accurate evaluation
     * of the input as a String.
     */
    @Test
    fun onEvaluateValidSimpleExpression() = runBlocking {

        Mockito.`when`(eval.execute(EXPRESSION))
                .thenReturn(
                        EvaluationResult.build { ANSWER }
                )

       //this is the "Unit" what we are testing
        presenter.onEvaluateClick(EXPRESSION)

        //These are the assertions which must be satisfied in order to pass the test
        Mockito.verify(eval).execute(EXPRESSION)
        Mockito.verify(viewModel).setDisplayState(ANSWER)
    }

    @Test
    fun onEvaluateInvalidExpression() = runBlocking {
        Mockito.`when`(eval.execute(INVALID_EXPRESSION))
                //...do this
                .thenReturn(
EvaluationResult.build { throw EvaluationError.ValidationError() }
                )

        presenter.onEvaluateClick(INVALID_EXPRESSION)

        Mockito.verify(eval).execute(INVALID_EXPRESSION)
        Mockito.verify(view).showError(VALIDATION_ERROR)
    }
}
