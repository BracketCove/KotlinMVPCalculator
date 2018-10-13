package com.wiseassblog.kotlincalculator

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.wiseassblog.kotlincalculator.dependencyinjection.Injector
import com.wiseassblog.kotlincalculator.util.attachFragment
import com.wiseassblog.kotlincalculator.view.CalculatorFragment


private const val VIEW: String = "VIEW"

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val manager: FragmentManager = this.supportFragmentManager

        val view = manager.findFragmentByTag(VIEW) as CalculatorFragment?
                ?: CalculatorFragment.newInstance(Injector(this))

        attachFragment(manager, view)

    }

}
