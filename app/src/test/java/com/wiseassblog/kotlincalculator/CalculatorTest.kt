package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.data.CalculatorImpl
import com.wiseassblog.kotlincalculator.viewmodel.DisplayVM
import io.reactivex.Flowable
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by R_KAY on 1/6/2018.
 */
class CalculatorTest {
    private val calc = CalculatorImpl()

    object SimpleExpression {
        @JvmStatic
        val EXPRESSION = "2+2"
        @JvmStatic
        val ANSWER = "4"
    }

    object ComplexExpression {
        @JvmStatic
        val EXPRESSION = "2+2-1*3+4"
        @JvmStatic
        val ANSWER = "5"
    }

    object InvalidExpression {
        @JvmStatic
        val EXPRESSION = "2+Q"
        @JvmStatic
        val ANSWER = "Error: Invalid Expression"
    }

    @Test
    fun onEvaluateValidSimpleExpression() {
       var subscriber = TestSubscriber<DisplayVM>()

       calc.evaluateExpression(ComplexExpression.EXPRESSION).subscribeWith(subscriber)

        subscriber.assertValue()


    }
}





