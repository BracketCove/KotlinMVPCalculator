package com.wiseassblog.kotlincalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.TextView
import com.wiseassblog.kotlincalculator.presenter.CalculatorPresenter
import com.wiseassblog.kotlincalculator.view.CalculatorFragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CalculatorActivity : AppCompatActivity() {

    companion object {
        val VIEW:String = "VIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val manager:FragmentManager = this.supportFragmentManager

        //Elvis Operator:
        //if (object/function) returns/is null :? do this
        val view = manager.findFragmentByTag(VIEW) as CalculatorFragment?
                ?: CalculatorFragment.newInstance()

        manager.beginTransaction().replace(R.id.root_activity_calculator, view).commit()

    }
}
