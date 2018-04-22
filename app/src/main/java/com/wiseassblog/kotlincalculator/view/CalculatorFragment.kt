package com.wiseassblog.kotlincalculator.view


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.wiseassblog.kotlincalculator.CalculatorActivity
import com.wiseassblog.kotlincalculator.R
import com.wiseassblog.kotlincalculator.dependencyinjection.Injector
import kotlinx.android.synthetic.main.fragment_calculator.*


/**
 * Please note: This class uses experimental synthetic layouts from kotlin-android-extensions lib
 * (see build.gradle plugin)
 *
 * A simple [Fragment] subclass.
 */
class CalculatorFragment : Fragment(), IViewContract.View, View.OnClickListener, View.OnLongClickListener {
    override fun onLongClick(v: View?): Boolean {
        when (v?.id) {
            R.id.btn_display_delete -> presenter.onLongDeleteClick()
        }

        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_evaluate -> presenter.onEvaluateClick(lbl_display.text.toString())
            R.id.btn_display_delete -> presenter.onDeleteClick()
            else -> {
                if (v is Button) {
                    presenter.onInputButtonClick(v.text.toString())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind()
    }

    override fun onStop() {
        super.onStop()
        presenter.clear()
    }


    lateinit var presenter: IViewContract.Presenter

    companion object {
        fun newInstance(injector:Injector) = CalculatorFragment().setPresenter(injector)
    }

    private fun setPresenter(injector:Injector):Fragment{
        presenter = injector.providePresenter(this)
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Sure glad I don't have to write this in java...
        btn_evaluate.setOnClickListener(this)
        btn_decimal.setOnClickListener(this)
        btn_display_delete.setOnClickListener(this)
        btn_display_delete.setOnLongClickListener(this)

        btn_number_one.setOnClickListener(this)
        btn_number_two.setOnClickListener(this)
        btn_number_three.setOnClickListener(this)
        btn_number_four.setOnClickListener(this)
        btn_number_five.setOnClickListener(this)
        btn_number_six.setOnClickListener(this)
        btn_number_seven.setOnClickListener(this)
        btn_number_eight.setOnClickListener(this)
        btn_number_nine.setOnClickListener(this)
        btn_number_zero.setOnClickListener(this)

        btn_operator_add.setOnClickListener(this)
        btn_operator_subtract.setOnClickListener(this)
        btn_operator_multiply.setOnClickListener(this)
        btn_operator_divide.setOnClickListener(this)
    }

    /*---------------- Interface ----------------*/
    override fun getCurrentExpression(): String {
        return lbl_display.text.toString()
    }

    override fun setDisplay(value: String) {
        lbl_display.text = value
    }

    override fun showError(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
    }

    override fun restartFeature() {
        val i = Intent(this.activity, CalculatorActivity::class.java)
        this.activity.finish()
        startActivity(i)
    }


}
