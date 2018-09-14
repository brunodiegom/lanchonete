package com.brunodiegom.lanchonete.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brunodiego.calculadoraanimal.component.Logger
import com.brunodiegom.lanchonete.R
import com.brunodiegom.lanchonete.model.Offers
import com.brunodiegom.lanchonete.server.InitializerListener
import com.brunodiegom.lanchonete.view.activity.adapter.OfferAdapter
import kotlinx.android.synthetic.main.offer_fragment.*
import org.koin.android.ext.android.inject

class OfferFragment : Fragment(), InitializerListener {

    private val offers: Offers by inject()

    private lateinit var viewAdapter: OfferAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.offer_fragment, container, false)
        offers.setListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated - isInitialized: ${offers.isInitialized}")
        if (offers.isInitialized) {
            setAdapter()
        }
    }

    override fun onInitializeFinish() {
        setAdapter()
    }

    private fun setAdapter() {
        viewManager = LinearLayoutManager(context)
        viewAdapter = OfferAdapter(offers.getOffers())
        offer_recycler_list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    companion object {
        private val TAG = Logger.tag
    }
}