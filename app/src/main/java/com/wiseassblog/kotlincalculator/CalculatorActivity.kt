package com.wiseassblog.kotlincalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.wiseassblog.kotlincalculator.dependencyinjection.Injector
import com.wiseassblog.kotlincalculator.util.attachFragment
import com.wiseassblog.kotlincalculator.view.CalculatorView


private const val VIEW: String = "VIEW"

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val manager: FragmentManager = this.supportFragmentManager

        val view = manager.findFragmentByTag(VIEW) as CalculatorView?
                ?: CalculatorView.newInstance(Injector(this))

        attachFragment(manager, view)

    }

}
