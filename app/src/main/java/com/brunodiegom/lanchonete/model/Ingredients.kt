package com.brunodiegom.lanchonete.model

import android.content.Context
import com.brunodiegom.lanchonete.server.DataProvider
import com.brunodiegom.lanchonete.server.InitializerListener
import com.brunodiegom.lanchonete.server.RequestListener
import org.json.JSONArray
import org.json.JSONObject

/**
 * Ingredients holder.
 */
class Ingredients(context: Context) : RequestListener {

    private var ingredientsMap: MutableMap<Int, IngredientData> = HashMap()

    private lateinit var initializerListener: InitializerListener

    var isInitialized = false

    init {
        DataProvider(this).request(context, INGREDIENT_API)
    }

    /**
     * Get ingredient model object.
     *
     * @param id from ingredient.
     * @return ingredient model object.
     */
    private fun getIngredient(id: Int) = ingredientsMap[id]

    /**
     * Get all ingredient model object list.
     *
     * @return ingredient model object list.
     */
    fun getIngredients() = ingredientsMap.values.toCollection(ArrayList())

    /**
     * Get ingredient model object id list.
     *
     * @return ingredient model object id list.
     */
    fun getIngredientsIdList() = ingredientsMap.keys.toCollection(ArrayList())

    /**
     * Calculate ingredient price based on amount.
     *
     * @param id from ingredient.
     * @param amount of ingredients.
     * @return ingredient price.
     */
    fun calculatePrice(id: Int, amount: Int) = (ingredientsMap[id]?.price ?: 0.0) * amount

    /**
     * Set initializer listener.
     */
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
            val image = value.getString("image")
            ingredientsMap[id] = IngredientData(id, name, price, image)
        }
        isInitialized = true
        initializerListener.onInitializeFinish()
    }

    /**
     * Calculate full hamburger price based on ingredients list.
     *
     * @param values ingredients id list.
     * @return full price.
     */
    fun calculateFullPrice(values: ArrayList<Int>): Double {
        var result = 0.0
        for (index in values) {
            getPrice(index)?.let { result += it }
        }
        return result
    }

    /**
     * Parse ingredient list adding a separator between them.
     *
     * @param idList ingredients id list.
     * @return ingredient list.
     */
    fun parseIngredientsList(idList: ArrayList<Int>): String {
        val result = arrayListOf<String>()
        for (id in idList) {
            getIngredient(id)?.let { result.add(it.name) }
        }
        return result.joinToString(separator = " • ")
    }

    private fun getPrice(id: Int) = ingredientsMap[id]?.price

    companion object {
        private const val INGREDIENT_API = DataProvider.SERVER_ADDRESS + "ingrediente"
    }
}