package com.brunodiegom.lanchonete.model

import com.brunodiegom.lanchonete.server.DataProvider
import org.json.JSONArray
import org.json.JSONObject

class Hamburgers(inputData: JSONArray) {

    val dataSet = arrayListOf<HamburgerData>()

    init {
        parseData(inputData)
    }

    private fun parseData(inputData: JSONArray) {
        for (index in 0 until inputData.length()) {
            val value = inputData.get(index) as JSONObject
            val id = value.getInt("id")
            val name = value.getString("name")
            val ingredients = parseIngredients(value.getJSONArray("ingredients"))
            val image = value.getString("image")
            dataSet.add(HamburgerData(id, name, ingredients, image.replaceFirst("https", "http")))
        }
    }

    private fun parseIngredients(values: JSONArray): ArrayList<Int> {
        val ingredients = arrayListOf<Int>()
        for (index in 0 until values.length()) {
            ingredients.add(values.get(index) as Int)
        }
        return ingredients
    }

    companion object {
        const val HAMBURGER_API = DataProvider.SERVER_ADDRESS + "lanche"
    }
}