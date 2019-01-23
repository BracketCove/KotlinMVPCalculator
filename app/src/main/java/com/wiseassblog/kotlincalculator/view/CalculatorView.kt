package com.wiseassblog.kotlincalculator.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
class CalculatorView : Fragment(), IViewContract.View {

    override fun onStart() {
        super.onStart()
        presenter.onEvent(ViewEvent.OnStart)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onEvent(ViewEvent.OnDestroy)
    }

    lateinit var presenter: IViewContract.Presenter

    companion object {
        fun newInstance(injector: Injector) = CalculatorView().setPresenter(injector)
    }

    private fun setPresenter(injector: Injector): Fragment {
        presenter = injector.providePresenter(this)
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_calculator, container, false)
    }

    /*---------------- Interface ----------------*/
    override fun bindEventListener() {
        //Sure glad I don't have to write this in java...
        btn_evaluate.setOnClickListener { presenter.onEvent(ViewEvent.OnEvaluateClick) }
        btn_decimal.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.DECIMAL)) }
        btn_display_delete.setOnClickListener { presenter.onEvent(ViewEvent.OnDeleteClick) }
        btn_display_delete.setOnLongClickListener {
            presenter.onEvent(ViewEvent.OnLongDeleteClick)
            true
            //true is required to avoid calling onClick immediately after onLongClick
        }

        btn_number_one.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.ONE)) }
        btn_number_two.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.TWO)) }
        btn_number_three.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.THREE)) }
        btn_number_four.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.FOUR)) }
        btn_number_five.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.FIVE)) }
        btn_number_six.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.SIX)) }
        btn_number_seven.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.SEVEN)) }
        btn_number_eight.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.EIGHT)) }
        btn_number_nine.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.NINE)) }
        btn_number_zero.setOnClickListener { presenter.onEvent(ViewEvent.OnOperandClick(Inputs.ZERO)) }

        btn_operator_add.setOnClickListener { presenter.onEvent(ViewEvent.OnOperatorClick(Inputs.PLUS)) }
        btn_operator_subtract.setOnClickListener { presenter.onEvent(ViewEvent.OnOperatorClick(Inputs.MINUS)) }
        btn_operator_multiply.setOnClickListener { presenter.onEvent(ViewEvent.OnOperatorClick(Inputs.MULTIPLY)) }
        btn_operator_divide.setOnClickListener { presenter.onEvent(ViewEvent.OnOperatorClick(Inputs.DIVIDE)) }
    }

    override fun getCurrentExpression() = lbl_display.text.toString()

    override fun setDisplay(value: String) {
        lbl_display?.text = value
    }

    override fun showError(value: String) = Toast.makeText(
            activity,
            value,
            Toast.LENGTH_SHORT
    ).show()

    override fun restartFeature() {
        val i = Intent(this.activity, CalculatorActivity::class.java)
        this.activity?.finish()
        startActivity(i)
    }
}
