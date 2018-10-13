package com.wiseassblog.kotlincalculator.util

/**
 * Simple EvaluationError handling class, designating errors which occur in either the
 * validator or the calculator
 * Created by R_KAY on 6/20/2018.
 */

const val VALIDATION_ERROR = "Invalid Expression."
const val COMPUTATION_ERROR = "Computation Failed."

sealed class EvaluationError(message: String? = null): Exception(message) {
    class ValidationError: EvaluationError(VALIDATION_ERROR)
    class CalculationError: EvaluationError(COMPUTATION_ERROR)
}