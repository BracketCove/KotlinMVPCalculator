package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.view.IViewContract
import com.wiseassblog.kotlincalculator.viewmodel.DisplayVM
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
 * test/resources/mockito-extesnsions to enable Mocking of Kotlin final classes.
 *
 * https://antonioleiva.com/mockito-2-kotlin/
 *
 */
class CalculatorPresenterTest {

    private lateinit var scheduler: TestScheduler

    private lateinit var presenter: CalculatorPresenter

    @Mock private lateinit var view: IViewContract.View

    //Although I personally prefer the term "Data" instead of "Model" to refer to an Architectural
    //Layer responsible for Data Management and Manipulation, you can think of calculator as the
    //"Model"; in a more classic sense of MVP
    @Mock private lateinit var calculator: ICalculator

    object SimpleExpression{
        @JvmStatic val EXPRESSION = "2+2"
        @JvmStatic val ANSWER = "4"
    }

    object ComplexExpression{
        @JvmStatic val EXPRESSION = "2+2-1*3+4"
        @JvmStatic val ANSWER = "5"
    }

    object InvalidExpression{
        @JvmStatic val EXPRESSION = "2+Q"
        @JvmStatic val ANSWER = "Error: Invalid Expression"
    }



    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        scheduler = TestScheduler()

        presenter = CalculatorPresenter(view, scheduler, calculator)
    }

    /**
     * User hits evaluate, expression is valid. Should return mathematically accurate evaluation
     * of the input as a String.
     */
    @Test
    fun onEvaluateValidSimpleExpression(){
        //when this method is called...
        Mockito.`when`(calculator.evaluateExpression(SimpleExpression.EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.just(
                                DisplayVM.createSuccessModel(SimpleExpression.ANSWER)
                        )
                )

        presenter.onEvaluateClick(SimpleExpression.EXPRESSION)

        Mockito.verify(calculator).evaluateExpression(SimpleExpression.EXPRESSION)
        Mockito.verify(view).setDisplay(SimpleExpression.ANSWER)

    }

    @Test
    fun onEvaluateInvalidExpression(){
        Mockito.`when`(calculator.evaluateExpression(InvalidExpression.EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.just(
                                DisplayVM.createFailureModel(InvalidExpression.ANSWER)
                        )
                )

        presenter.onEvaluateClick(InvalidExpression.EXPRESSION)

        Mockito.verify(calculator).evaluateExpression(InvalidExpression.EXPRESSION)
        Mockito.verify(view).showError(InvalidExpression.ANSWER)
    }

    @Test
    fun onEvaluateFatalError(){
        Mockito.`when`(calculator.evaluateExpression(InvalidExpression.EXPRESSION))
                //...do this
                .thenReturn(
                        Flowable.error(Exception(InvalidExpression.ANSWER))
                )

        presenter.onEvaluateClick(InvalidExpression.EXPRESSION)

        Mockito.verify(calculator).evaluateExpression(InvalidExpression.EXPRESSION)
        Mockito.verify(view).restartFeature()
    }


}
