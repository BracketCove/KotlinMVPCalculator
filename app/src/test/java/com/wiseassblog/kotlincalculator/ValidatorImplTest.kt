package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.data.ValidatorImpl
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
    val INVALID_EXPRESSION_FOUR = "."
    val INVALID_EXPRESSION_FIVE = "2..0+2"


    @Test
    fun validExpressionTestOne(){

        //all we're saying here is: assert that validator returns true when given a valid simple
        //expression
        assertTrue(validator.validateExpression(SIMPLE_EXPRESSION))

    }

    @Test
    fun validExpressionTestTwo(){

        assertTrue(validator.validateExpression(COMPLEX_EXPRESSION))

    }

    @Test
    fun invalidExpressionTestOne(){

       assertFalse(validator.validateExpression(INVALID_EXPRESSION_ONE))

    }

    @Test
    fun invalidExpressionTestTwo(){

        assertFalse(validator.validateExpression(INVALID_EXPRESSION_TWO))

    }

    @Test
    fun invalidExpressionTestThree(){

        assertFalse(validator.validateExpression(INVALID_EXPRESSION_THREE))

    }

    @Test
    fun invalidExpressionTestFour(){

        assertFalse(validator.validateExpression(INVALID_EXPRESSION_FOUR))

    }

    @Test
    fun invalidExpressionTestFive(){

        assertFalse(validator.validateExpression(INVALID_EXPRESSION_FIVE))

    }


}