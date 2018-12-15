package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.data.implementations.ValidatorImpl
import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.util.VALIDATION_ERROR
import org.junit.jupiter.api.Test
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
        val result = validator.validateExpression(SIMPLE_EXPRESSION)

        if (result is EvaluationResult.Value) assertTrue(result.value)
        else assertTrue { false }
    }

    @Test
    fun validExpressionTestTwo(){
        val result = validator.validateExpression(COMPLEX_EXPRESSION)

        if (result is EvaluationResult.Value) assertTrue(result.value)
    }

    @Test
    fun invalidExpressionTestOne(){

        val result = validator.validateExpression(INVALID_EXPRESSION_ONE)

        if (result is EvaluationResult.Error) assertTrue(result.error.message == VALIDATION_ERROR)
    }

    @Test
    fun invalidExpressionTestTwo(){

        val result = validator.validateExpression(INVALID_EXPRESSION_TWO)

        if (result is EvaluationResult.Error) assertTrue(result.error.message == VALIDATION_ERROR)
    }

    @Test
    fun invalidExpressionTestThree(){

        val result = validator.validateExpression(INVALID_EXPRESSION_THREE)

        if (result is EvaluationResult.Error) assertTrue(result.error.message == VALIDATION_ERROR)
    }

    @Test
    fun invalidExpressionTestFour(){

        val result = validator.validateExpression(INVALID_EXPRESSION_FOUR)

        if (result is EvaluationResult.Error) assertTrue(result.error.message == VALIDATION_ERROR)

    }

    @Test
    fun invalidExpressionTestFive(){

        val result = validator.validateExpression(INVALID_EXPRESSION_FIVE)

        if (result is EvaluationResult.Error) assertTrue(result.error.message == VALIDATION_ERROR)

    }
}