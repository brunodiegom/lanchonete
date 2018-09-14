package com.brunodiegom.lanchonete.model

import com.brunodiegom.lanchonete.server.DataProvider
import org.json.JSONArray
import org.json.JSONObject

class Orders(inputData: JSONArray) {

    val dataSet = arrayListOf<OrderData>()

    init {
        parseData(inputData)
    }

    private fun parseData(inputData: JSONArray) {
        for (index in 0 until inputData.length()) {
            val value = inputData.get(index) as JSONObject
            val id = value.getInt("id")
            val idHamburger = value.getInt("id_sandwich")
            val extras = parseIngredients(value.getJSONArray("extras"))
            dataSet.add(OrderData(id, idHamburger, extras))
        }
    }

    private fun parseIngredients(values: JSONArray): ArrayList<Int> {
        val extras = arrayListOf<Int>()
        for (index in 0 until values.length()) {
            extras.add(values.get(index) as Int)
        }
        return extras
    }

    companion object {
        const val ORDER_API = DataProvider.SERVER_ADDRESS + "pedido"
    }
}