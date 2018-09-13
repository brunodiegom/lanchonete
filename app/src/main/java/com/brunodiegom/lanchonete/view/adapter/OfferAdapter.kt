package com.brunodiegom.lanchonete.view.activity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brunodiegom.lanchonete.R
import com.brunodiegom.lanchonete.model.OfferData
import kotlinx.android.synthetic.main.offer_list_adapter.view.*

internal class OfferAdapter(private val dataSet: ArrayList<OfferData>) :
        RecyclerView.Adapter<OfferAdapter.ViewHolder>() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.offer_name.text = dataSet[position].name
        holder.itemView.description.text = dataSet[position].description
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
