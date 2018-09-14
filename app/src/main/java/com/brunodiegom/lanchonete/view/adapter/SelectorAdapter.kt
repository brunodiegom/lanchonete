package com.brunodiegom.lanchonete.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brunodiegom.lanchonete.R
import com.brunodiegom.lanchonete.model.IngredientData
import com.brunodiegom.lanchonete.view.component.SelectorAdapterChangeListener
import com.brunodiegom.lanchonete.view.component.SelectorChangeListener
import kotlinx.android.synthetic.main.selector_adapter.view.*

/**
 * Selector adapter to customize ingredients.
 */
class SelectorAdapter(private val dataSet: ArrayList<IngredientData>) :
        RecyclerView.Adapter<SelectorAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var selectorAdapterChangeListener: SelectorAdapterChangeListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.selector_adapter, parent, false))
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val price = String.format(PRICE_FORMAT, dataSet[position].price)
        holder.itemView.selector_item.setName("${dataSet[position].name} • $price")
    }

    /**
     * Set selector adapter change listener.
     *
     * @param listener selector change listener.
     */
    fun setListener(listener: SelectorAdapterChangeListener) {
        selectorAdapterChangeListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), SelectorChangeListener {
        init {
            itemView.selector_item.setListener(this)
        }

        override fun onSelectorChanged(value: Int) {
            selectorAdapterChangeListener.onSelectorAdapterChanged(value, dataSet[adapterPosition].id)
        }
    }

    companion object {
        const val PRICE_FORMAT = "R$%.2f"
    }
}
