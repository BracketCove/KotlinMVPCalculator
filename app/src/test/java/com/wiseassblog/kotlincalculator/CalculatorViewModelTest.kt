package com.wiseassblog.kotlincalculator

import com.wiseassblog.kotlincalculator.viewmodel.CalculatorViewModel
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Created by R_KAY on 2/1/2018.
 */
class CalculatorViewModelTest {


    val STATE = "LOLOLOLOLOLOLOLOLOLOL"

    @Test
    fun onSetDisplayState() {
        val viewModel = CalculatorViewModel()

        val subscriber = TestSubscriber<String>()

        viewModel.getDisplayStatePublisher().subscribeWith(subscriber)
        viewModel.setDisplayState(STATE)

        assertTrue(subscriber.values()[0] == STATE)
    }

}