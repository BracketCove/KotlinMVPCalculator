package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.data.ValidatorImpl
import com.wiseassblog.kotlincalculator.data.datamodel.Expression
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by R_KAY on 1/20/2018.
 */
class ValidatorImplTest {

    private val validator = ValidatorImpl

    val COMPLEX_EXPRESSION = "2+2-1*3+4"
    val SIMPLE_EXPRESSION = "2+2"
    val INVALID_EXPRESSION_ONE = "2+"
    val INVALID_EXPRESSION_TWO = "+2"
    val INVALID_EXPRESSION_THREE = "2+-2"


    @Test
    fun validExpressionTestOne(){
        val subscriber = TestSubscriber<Expression>()

        validator.validateExpression(SIMPLE_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].isValid)
    }

    @Test
    fun validExpressionTestTwo(){
        var subscriber = TestSubscriber<Expression>()

        validator.validateExpression(COMPLEX_EXPRESSION).subscribeWith(subscriber)

        assertTrue(subscriber.values()[0].isValid)
    }

    @Test
    fun invalidExpressionTestOne(){
        val subscriber = TestSubscriber<Expression>()

        validator.validateExpression(INVALID_EXPRESSION_ONE).subscribeWith(subscriber)

        assertFalse(subscriber.values()[0].isValid)
    }

    @Test
    fun invalidExpressionTestTwo(){
        val subscriber = TestSubscriber<Expression>()

        validator.validateExpression(INVALID_EXPRESSION_TWO).subscribeWith(subscriber)

        assertFalse(subscriber.values()[0].isValid)
    }

    @Test
    fun invalidExpressionTestThree(){
        val subscriber = TestSubscriber<Expression>()

        validator.validateExpression(INVALID_EXPRESSION_THREE).subscribeWith(subscriber)

        assertFalse(subscriber.values()[0].isValid)
    }


}