package com.brunodiegom.lanchonete.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brunodiegom.lanchonete.R
import com.brunodiegom.lanchonete.model.HamburgerData
import com.brunodiegom.lanchonete.model.Ingredients
import com.brunodiegom.lanchonete.model.OrderData
import com.brunodiegom.lanchonete.server.RequestController
import kotlinx.android.synthetic.main.cart_list_adapter.view.*

internal class CartAdapter(
        private val orders: ArrayList<OrderData>,
        private val ingredients: Ingredients,
        private val hamburgers: ArrayList<HamburgerData>):
        RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hamburger = getHamburger(orders[position].idHamburger)
        holder.itemView.cart_photo.setDefaultImageResId(R.mipmap.ic_launcher)
        holder.itemView.cart_photo.setImageUrl(hamburger?.imageLink, RequestController.getInstance(context).imageLoader)
        holder.itemView.cart_ingredients.text = ingredients.parseIngredientsList(hamburger!!.ingredients)
        holder.itemView.cart_price.text = String.format(PRICE_FORMAT, ingredients.calculateFullPrice(hamburger.ingredients))

        if (orders[position].extras.size > 0) {
            holder.itemView.cart_name.text = String.format(context.getString(R.string.your_way), hamburger.name)
            holder.itemView.extra_price.text = String.format(EXTRA_PRICE_FORMAT, ingredients.calculateFullPrice(orders[position].extras))
            holder.itemView.extra_price.visibility = View.VISIBLE
            holder.itemView.extras.text = ingredients.parseIngredientsList(orders[position].extras)
            holder.itemView.extras.visibility = View.VISIBLE
        } else {
            holder.itemView.cart_name.text = hamburger.name
        }
    }

    private fun getHamburger(id: Int) : HamburgerData? {
        for (hamburger in hamburgers) {
            if (hamburger.id == id) return hamburger
        }
        return null
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        const val PRICE_FORMAT = "R$%.2f"
        const val EXTRA_PRICE_FORMAT = "+$PRICE_FORMAT"
    }
}
