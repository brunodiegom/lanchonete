package com.brunodiegom.lanchonete.model

import android.content.Context
import com.brunodiegom.lanchonete.server.DataProvider
import com.brunodiegom.lanchonete.server.InitializerListener
import com.brunodiegom.lanchonete.server.RequestListener
import org.json.JSONArray
import org.json.JSONObject

class Offers(context: Context) : RequestListener {

    private var offersMap: MutableMap<Int, OfferData> = HashMap()

    private lateinit var initializerListener: InitializerListener

    var isInitialized = false

    init {
        DataProvider(this).request(context, OFFER_API)
    }

    fun getOffers() = offersMap.values.toCollection(ArrayList())

    fun setListener(listener: InitializerListener) {
        initializerListener = listener
    }

    override fun onRequestResult(data: JSONArray) {
        parseData(data)
    }

    override fun onPutResult(data: JSONObject) {}

    private fun parseData(data: JSONArray) {
        for (index in 0 until data.length()) {
            val value = data.get(index) as JSONObject
            val id = value.getInt("id")
            val name = value.getString("name")
            val description = value.getString("description")
            offersMap[id] = OfferData(id, name, description)
        }
        isInitialized = true
        initializerListener.onInitializeFinish()
    }

    companion object {
        private const val OFFER_API = DataProvider.SERVER_ADDRESS + "promocao"
    }
}