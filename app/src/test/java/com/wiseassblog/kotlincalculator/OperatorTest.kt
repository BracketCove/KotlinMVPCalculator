package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.data.datamodel.OperatorDataModel
import org.junit.Test

/**
 *
 * Created by R_KAY on 1/8/2018.
 */
class OperatorTest {

    val MULTIPLY = "*"
    val DIVIDE = "/"
    val ADD = "+"
    val SUBTRACT = "-"

    @Test
    fun TestEvaluateFirst() {
        val testOp = OperatorDataModel(MULTIPLY)

        //assert true
        assert(testOp.evaluateFirst)
    }

    @Test
    fun TestEvaluateLast() {
        val testOp = OperatorDataModel(ADD)

        //assert true
        assert(!testOp.evaluateFirst)
    }
}