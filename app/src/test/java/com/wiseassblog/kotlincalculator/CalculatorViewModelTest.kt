package com.wiseassblog.kotlincalculator

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.wiseassblog.kotlincalculator.viewmodel.CalculatorViewModel
import org.junit.Test
import org.mockito.Mock
import android.arch.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertTrue

/**
 * Created by R_KAY on 2/1/2018.
 */
class CalculatorViewModelTest {

    /*@JvmField causes the generated JVM field (the rule) to be public, which is necessary
    for it to work.*/
    @JvmField
    @Rule
    public val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<String>

    val STATE = "LOLOLOLOLOLOLOLOLOLOL"


    @Test
    fun onSetDisplayState() {
        MockitoAnnotations.initMocks(this)

        val viewModel = CalculatorViewModel()

        viewModel.setObserver(observer)
        viewModel.setDisplayState(STATE)

        Mockito.verify(observer).onChanged(STATE)
    }

}