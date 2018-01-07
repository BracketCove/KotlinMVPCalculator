package com.wiseassblog.kotlincalculator.domain

import io.reactivex.Flowable

/**
 *
 * Created by R_KAY on 12/20/2017.
 */
interface BaseUseCase<T> {

    fun clean()

    fun execute(expression: String): Flowable<T>
}

