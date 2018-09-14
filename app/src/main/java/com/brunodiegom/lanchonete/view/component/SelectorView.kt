package com.brunodiegom.lanchonete.view.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.brunodiegom.lanchonete.R
import kotlinx.android.synthetic.main.ingredient_selector.view.*

/**
 * Custom view to select item amount.
 */
class SelectorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    private var amount = 0
    private lateinit var selectorChangeListener: SelectorChangeListener

    init {
        LayoutInflater.from(context).inflate(R.layout.ingredient_selector, this, true)

        minus_button.isEnabled = false

        minus_button.setOnClickListener {
            amount--
            if (amount == 0) {
                it.isEnabled = false
            }
            setValue(amount)
        }

        plus_button.setOnClickListener {
            amount++
            if (amount > 0) {
                minus_button.isEnabled = true
            }
            setValue(amount)
        }
    }

    /**
     * Set selector title.
     *
     * @param title to be set on selector.
     */
    fun setName(title: String) {
        selector_title.text = title
    }

    /**
     * Returns selected value.
     *
     * @return value defined by user.
     */
    fun getValue() = amount

    /**
     * Set selector change listener.
     *
     * @param listener selector change listener.
     */
    fun setListener(listener: SelectorChangeListener) {
        selectorChangeListener = listener
    }

    private fun setValue(value: Int) {
        selectorChangeListener.onSelectorChanged(value)
        selector_value.text = amount.toString()
    }
}