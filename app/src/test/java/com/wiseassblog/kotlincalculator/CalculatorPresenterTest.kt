package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorVM
import io.reactivex.Flowable
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

    private lateinit var scheduler: TestScheduler

    private lateinit var presenter: CalculatorPresenter

    @Mock
    private lateinit var view: IViewContract.View

    //Although I personally prefer the term "Data" instead of "Model" to refer to an Architectural
    //Layer responsible for Data Management and Manipulation, you can think of calculator as the
    //"Model"; in a more classic sense of MVP
    @Mock
    private lateinit var calculator: ICalculator

    val EXPRESSION = "2+2"
    val ANSWER = "4"

    val INVALID_EXPRESSION = "2+Q"
    val INVALID_ANSWER = "Error: Invalid Expression"


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        scheduler = TestScheduler()

        presenter = CalculatorPresenter(view, scheduler, calculator)
    }

    /**
     * User hits evaluate, expression is valid. Should return mathematically accurate evaluation
     * of the input as a String.
     */
    @Test
    fun onEvaluateValidSimpleExpression() {
        //when this method is called...
        Mockito.`when`(calculator.evaluateExpression(EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.just(
                                CalculatorVM.createSuccessModel(ANSWER)
                        )
                )

        //this is the "Unit" what we are testing
        presenter.onEvaluateClick(EXPRESSION)

        //These are the assertions which must be satisfied in order to pass the test
        Mockito.verify(calculator).evaluateExpression(EXPRESSION)
        Mockito.verify(view).setDisplay(ANSWER)

    }

    @Test
    fun onEvaluateInvalidExpression() {
        Mockito.`when`(calculator.evaluateExpression(INVALID_EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.just(
                                CalculatorVM.createFailureModel(INVALID_ANSWER)
                        )
                )

        presenter.onEvaluateClick(INVALID_EXPRESSION)

        Mockito.verify(calculator).evaluateExpression(INVALID_EXPRESSION)
        Mockito.verify(view).showError(INVALID_ANSWER)
    }

    @Test
    fun onEvaluateFatalError() {
        Mockito.`when`(calculator.evaluateExpression(INVALID_EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.error(Exception(INVALID_ANSWER))
                )

        presenter.onEvaluateClick(INVALID_EXPRESSION)

        Mockito.verify(calculator).evaluateExpression(INVALID_EXPRESSION)
        Mockito.verify(view).restartFeature()
    }


}
