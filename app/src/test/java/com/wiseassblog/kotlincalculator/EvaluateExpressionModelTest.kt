package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.data.implementations.CalculatorImpl
import com.wiseassblog.kotlincalculator.data.implementations.EvaluatorImpl
import com.wiseassblog.kotlincalculator.data.implementations.ValidatorImpl
import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Created by R_KAY on 1/17/2018.
 */
class EvaluateExpressionModelTest {

    val calculator: CalculatorImpl = mockk()

    val validator: ValidatorImpl = mockk()

    val eval = EvaluatorImpl(calculator, validator)

    val EXPRESSION = "2+2"
    val ANSWER = "4"

    @Test
    fun onEvaluateExpression() = runBlocking {

        every { validator.validateExpression(EXPRESSION) } returns EvaluationResult.build { true }

        coEvery { calculator.evaluateExpression(EXPRESSION) } returns EvaluationResult.build { ANSWER }


        val result = eval.evaluateExpression(EXPRESSION)

        verify { validator.validateExpression(EXPRESSION) }
        coVerify { calculator.evaluateExpression(EXPRESSION) }

        if (result is EvaluationResult.Value) {
            assertTrue { result.value == ANSWER }
        } else {
            //fail
            assertTrue { false }
        }
    }

}