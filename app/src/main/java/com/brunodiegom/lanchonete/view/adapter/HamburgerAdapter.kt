package com.brunodiegom.lanchonete.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brunodiegom.lanchonete.R
import com.brunodiegom.lanchonete.model.HamburgerData
import com.brunodiegom.lanchonete.model.Ingredients
import com.brunodiegom.lanchonete.server.RequestController
import com.brunodiegom.lanchonete.tools.RecyclerViewClickListener
import kotlinx.android.synthetic.main.hamburger_list_adapter.view.*

internal class HamburgerAdapter(private val dataSet: ArrayList<HamburgerData>, val ingredients: Ingredients) :
        RecyclerView.Adapter<HamburgerAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var recyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hamburger_list_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.photo.setDefaultImageResId(R.mipmap.ic_launcher)
        holder.itemView.photo.setImageUrl(dataSet[position].imageLink, RequestController.getInstance(context).imageLoader)
        holder.itemView.name.text = dataSet[position].name
        holder.itemView.ingredients.text = ingredients.parseIngredientsList(dataSet[position].ingredients)
        holder.itemView.price.text = String.format(PRICE_FORMAT, ingredients.calculateFullPrice(dataSet[position].ingredients))
    }

    fun setRecyclerViewClickListener(listener: RecyclerViewClickListener) {
        recyclerViewClickListener = listener
    }

    fun getItem(position: Int) = dataSet[position]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            recyclerViewClickListener.onClickListener(view, adapterPosition)
        }
    }

    companion object {
        const val PRICE_FORMAT = "R$%.2f"
    }
}
