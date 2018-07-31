package com.wiseassblog.kotlincalculator.data

import android.support.annotation.VisibleForTesting
import com.wiseassblog.kotlincalculator.data.datamodel.OperandDataModel
import com.wiseassblog.kotlincalculator.data.datamodel.OperatorDataModel
import com.wiseassblog.kotlincalculator.domain.domainmodel.ExpressionResult
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import java.lang.IllegalArgumentException

/**
 * Created by R_KAY on 12/21/2017.
 */
object CalculatorImpl : ICalculator {
    override suspend fun evaluateExpression(expression: String): ExpressionResult<Exception, String> {
        val operatorDataModels: MutableList<OperatorDataModel> = getOperators(expression)
        val operands: MutableList<OperandDataModel> = getOperands(expression)

        while (operands.size > 1) {
            val firstOperand = operands[0]
            val secondOperand = operands[1]
            val firstOperator = operatorDataModels[0]

            //if op is * or / (evaluateFirst), or no more operatorDataModels to follow,
            // or next op is NOT (evaluateFirst)
            if (firstOperator.evaluateFirst ||
                    operatorDataModels.elementAtOrNull(1) == null ||
                    !operatorDataModels[1].evaluateFirst) {
                val result = OperandDataModel(evaluatePair(firstOperand, secondOperand, firstOperator))
                operatorDataModels.remove(firstOperator)
                operands.remove(firstOperand)
                operands.remove(secondOperand)

                operands.add(0, result)
            } else {

                val thirdOperand = operands[2]
                val secondOperator = operatorDataModels[1]
                val result = OperandDataModel(evaluatePair(secondOperand, thirdOperand, secondOperator))

                operatorDataModels.remove(secondOperator)
                operands.remove(secondOperand)
                operands.remove(thirdOperand)

                operands.add(1, result)
            }
        }


        return ExpressionResult.build { operands[0].value }
    }

    @VisibleForTesting
    internal fun getOperands(expression: String): MutableList<OperandDataModel> {
        val operands = expression.split("+", "-", "/", "*")
        val outPut: MutableList<OperandDataModel> = arrayListOf()

        //Kotlin's answer to enhanced for loop
        operands.indices.mapTo(outPut) {
            OperandDataModel(operands[it])
        }
        return outPut
    }

    @VisibleForTesting
    internal fun getOperators(expression: String): MutableList<OperatorDataModel> {
        //this ugly stuff is called Regex; Regular ExpressionDataModel/
        //Basically saying split based on number or decimal numbers.
        val operators = expression.split("\\d+(?:\\.\\d+)?".toRegex())
                .filterNot { it == "" }
                .toMutableList()
        val outPut: MutableList<OperatorDataModel> = arrayListOf()

        operators.indices.mapTo(outPut) {
            OperatorDataModel(operators[it])
        }
        return outPut
    }

    @VisibleForTesting
    internal fun evaluatePair(firstOperand: OperandDataModel, secondOperand: OperandDataModel, operatorDataModel: OperatorDataModel): String {
        when (operatorDataModel.operatorValue) {
            "+" -> return (firstOperand.value.toFloat() + secondOperand.value.toFloat()).toString()
            "-" -> return (firstOperand.value.toFloat() - secondOperand.value.toFloat()).toString()
            "/" -> return (firstOperand.value.toFloat() / secondOperand.value.toFloat()).toString()
            "*" -> return (firstOperand.value.toFloat() * secondOperand.value.toFloat()).toString()
        }
        throw  IllegalArgumentException("Illegal Operator.")
    }

}