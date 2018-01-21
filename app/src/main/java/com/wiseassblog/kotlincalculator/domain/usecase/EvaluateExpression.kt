package com.wiseassblog.kotlincalculator.domain.usecase

import com.wiseassblog.kotlincalculator.domain.repository.IValidator
import com.wiseassblog.kotlincalculator.data.datamodel.Expression
import com.wiseassblog.kotlincalculator.domain.BaseUseCase
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.util.BaseSchedulerProvider
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorVM
import io.reactivex.Flowable
import io.reactivex.Flowable.zip
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

/**
 * Created by R_KAY on 12/20/2017.
 */
class EvaluateExpression(private val calculator: ICalculator,
                         private val validator: IValidator,
                         private val scheduler: BaseSchedulerProvider,
                         private val disposables:CompositeDisposable) : BaseUseCase<CalculatorVM> {

    override fun execute(expression: String): Flowable<CalculatorVM> {

        //Prepare Observables for zip operator
        val validationResult = validator.validateExpression(expression)
        val calculatorResult = calculator.evaluateExpression(expression)

        return zip(
                validationResult.subscribeOn(scheduler.getComputationScheduler()),
                calculatorResult.subscribeOn(scheduler.getComputationScheduler()),
                object : BiFunction<Expression, Expression, CalculatorVM> {
                    override fun apply(valid: Expression, evaluated: Expression): CalculatorVM {
                        //failed to validate, no need to proceed further
                        if (!valid.isValid) {
                            return CalculatorVM.createFailureModel(valid.value)
                        }

                        return CalculatorVM.createSuccessModel(evaluated.value)
                    }
                }
        )


    }

    override fun clean() {

    }
}