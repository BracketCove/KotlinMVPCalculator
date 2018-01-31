package com.wiseassblog.kotlincalculator.data

import android.support.annotation.VisibleForTesting
import com.wiseassblog.kotlincalculator.data.datamodel.Expression
import com.wiseassblog.kotlincalculator.data.datamodel.Operand
import com.wiseassblog.kotlincalculator.data.datamodel.Operator
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import io.reactivex.Flowable
import java.lang.IllegalArgumentException

/**
 * Created by R_KAY on 12/21/2017.
 */
object CalculatorImpl : ICalculator {
    override fun evaluateExpression(expression: String): Flowable<Expression> {

        return evaluate(expression)
    }

    private fun evaluate(expression: String): Flowable<Expression> {

        //get ops and ops
        val operators: MutableList<Operator> = getOperators(expression)
        val operands: MutableList<Operand> = getOperands(expression)

        while (operands.size > 1) {
            val firstOperand = operands[0]
            val secondOperand = operands[1]
            val firstOperator = operators[0]

            //if op is * or / (evaluateFirst), or no more operators to follow,
            // or next op is NOT (evaluateFirst)
            if (firstOperator.evaluateFirst ||
                    operators.elementAtOrNull(1) == null ||
                    !operators[1].evaluateFirst) {
                val result = Operand(evaluatePair(firstOperand, secondOperand, firstOperator))
                operators.remove(firstOperator)
                operands.remove(firstOperand)
                operands.remove(secondOperand)

                operands.add(0, result)
            } else {

                val thirdOperand = operands[2]
                val secondOperator = operators[1]
                val result = Operand(evaluatePair(secondOperand, thirdOperand, secondOperator))

                operators.remove(secondOperator)
                operands.remove(secondOperand)
                operands.remove(thirdOperand)

                operands.add(1, result)
            }
        }
        return Flowable.just(Expression(operands[0].value, true))
    }

    @VisibleForTesting
    internal fun getOperands(expression: String): MutableList<Operand> {
        val operands = expression.split("+", "-", "/", "*")
        val outPut: MutableList<Operand> = arrayListOf()

        //Kotlin's answer to enhanced for loop
        operands.indices.mapTo(outPut) {
            Operand(operands[it])
        }
        return outPut
    }

    @VisibleForTesting
    internal fun getOperators(expression: String): MutableList<Operator> {
        //this ugly stuff is called Regex; Regular Expression/
        //Basically saying split based on number or decimal numbers.
        val operators = expression.split("\\d+(?:\\.\\d+)?".toRegex())
                .filterNot { it == "" }
                .toMutableList()
        val outPut: MutableList<Operator> = arrayListOf()

        operators.indices.mapTo(outPut) {
            Operator(operators[it])
        }
        return outPut
    }

    @VisibleForTesting
    internal fun evaluatePair(firstOperand: Operand, secondOperand: Operand, operator: Operator): String {
        when (operator.operatorValue) {
            "+" -> return (firstOperand.value.toFloat() + secondOperand.value.toFloat()).toString()
            "-" -> return (firstOperand.value.toFloat() - secondOperand.value.toFloat()).toString()
            "/" -> return (firstOperand.value.toFloat() / secondOperand.value.toFloat()).toString()
            "*" -> return (firstOperand.value.toFloat() * secondOperand.value.toFloat()).toString()
        }
        throw  IllegalArgumentException("Illegal Operator.")
    }

}