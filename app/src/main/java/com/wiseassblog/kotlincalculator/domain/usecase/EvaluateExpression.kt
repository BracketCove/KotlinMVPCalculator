package com.wiseassblog.kotlincalculator.domain.usecase

import com.wiseassblog.kotlincalculator.domain.BaseUseCase
import com.wiseassblog.kotlincalculator.domain.repository.ICalculator
import com.wiseassblog.kotlincalculator.viewmodel.DisplayVM
import io.reactivex.Flowable

/**
 * Created by R_KAY on 12/20/2017.
 */
class EvaluateExpression(private val calculator: ICalculator): BaseUseCase<DisplayVM> {

    override fun execute(expression: String): Flowable<DisplayVM> {
            return Flowable.just(DisplayVM.createSuccessModel("Blah"))
    }

    override fun clean() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}