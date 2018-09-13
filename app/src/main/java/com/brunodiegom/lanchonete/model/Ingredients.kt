package com.brunodiegom.lanchonete.model

import android.content.Context
import com.brunodiegom.lanchonete.server.DataProvider
import com.brunodiegom.lanchonete.server.InitializerListener
import com.brunodiegom.lanchonete.server.RequestListener
import org.json.JSONArray
import org.json.JSONObject

class Ingredients(context: Context) : RequestListener {

    private var ingredientsMap: MutableMap<Int, IngredientData> = HashMap()

    private lateinit var initializerListener: InitializerListener

    var isInitialized = false

    init {
        DataProvider(this).request(context, INGREDIENT_API)
    }

    fun getIngredient(id: Int) = ingredientsMap[id]

    fun setListener(listener: InitializerListener) {
        initializerListener = listener
    }

    override fun onRequestResult(data: JSONArray) {
        parseData(data)
    }

    private fun parseData(data: JSONArray) {
        for (index in 0 until data.length()) {
            val value = data.get(index) as JSONObject
            val id = value.getInt("id")
            val name = value.getString("name")
            val price = value.getDouble("price")
            ingredientsMap[id] = IngredientData(id, name, price, "")
        }
        isInitialized = true
        initializerListener.onInitializeFinish()
    }

    fun calculateFullPrice(values: ArrayList<Int>): Double {
        var result = 0.0
        for (index in values) {
            getPrice(index)?.let { result += it }
        }
        return result
    }

    private fun getPrice(id: Int) = ingredientsMap[id]?.price

    companion object {
        private const val INGREDIENT_API = DataProvider.SERVER_ADDRESS + "ingrediente"
    }
}