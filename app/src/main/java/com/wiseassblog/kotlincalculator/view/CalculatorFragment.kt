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
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_calculator.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class CalculatorFragment : Fragment(), IViewContract.View {

    @Inject
    lateinit var presenter: IViewContract.Presenter

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    val listener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_evaluate -> presenter.onEvaluateClick()
            R.id.btn_display_delete -> presenter.onDeleteClick()
            else -> {
                if (v is Button) {
                    presenter.onInputButtonClick(v.text.toString())
                }
            }
        }
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
        btn_evaluate.setOnClickListener { listener }
        btn_decimal.setOnClickListener { listener }
        btn_display_delete.setOnClickListener { listener }

        btn_number_one.setOnClickListener { listener }
        btn_number_two.setOnClickListener { listener }
        btn_number_three.setOnClickListener { listener }
        btn_number_four.setOnClickListener { listener }
        btn_number_five.setOnClickListener { listener }
        btn_number_six.setOnClickListener { listener }
        btn_number_seven.setOnClickListener { listener }
        btn_number_eight.setOnClickListener { listener }
        btn_number_nine.setOnClickListener { listener }
        btn_number_zero.setOnClickListener { listener }

        btn_operator_add.setOnClickListener { listener }
        btn_operator_subtract.setOnClickListener { listener }
        btn_operator_multiply.setOnClickListener { listener }
        btn_operator_divide.setOnClickListener { listener }
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
