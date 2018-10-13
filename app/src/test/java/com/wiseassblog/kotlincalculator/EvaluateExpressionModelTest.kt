package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.data.ValidatorImpl
import com.wiseassblog.kotlincalculator.data.datamodel.ExpressionDataModel
import com.wiseassblog.kotlincalculator.domain.domainmodel.EvaluationResult
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.domain.usecase.EvaluateExpression
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertTrue

/**
 * Created by R_KAY on 1/17/2018.
 */
class EvaluateExpressionModelTest {

    @Mock
    lateinit var calc: ICalculator

    @Mock
    lateinit var validator: ValidatorImpl

    lateinit var useCase: EvaluateExpression

    val EXPRESSION = "2+2"
    val ANSWER = "4"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = EvaluateExpression(calc, validator)
    }

    @Test
    fun onUseCaseExecuted() = runBlocking {

        Mockito.`when`(validator.validateExpression(EXPRESSION))
                .thenReturn(
                        EvaluationResult.build { true }
                )

        Mockito.`when`(calc.evaluateExpression(EXPRESSION))
                .thenReturn(
                        EvaluationResult.build { ANSWER }
                )

        val result = useCase.execute(EXPRESSION)


        Mockito.verify(validator).validateExpression(EXPRESSION)
        Mockito.verify(calc).evaluateExpression(EXPRESSION)

        if (result is EvaluationResult.Value){
            assertTrue { result.value == ANSWER }
        } else {
            //fail
            assertTrue { false }
        }
    }

}