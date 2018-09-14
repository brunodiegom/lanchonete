package com.brunodiegom.lanchonete.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.brunodiego.calculadoraanimal.component.Logger
import com.brunodiegom.lanchonete.R
import com.brunodiegom.lanchonete.model.HamburgerData
import com.brunodiegom.lanchonete.model.Ingredients
import com.brunodiegom.lanchonete.server.DataProvider
import com.brunodiegom.lanchonete.server.RequestController
import com.brunodiegom.lanchonete.server.RequestListener
import com.brunodiegom.lanchonete.view.adapter.SelectorAdapter
import com.brunodiegom.lanchonete.view.component.SelectorAdapterChangeListener
import kotlinx.android.synthetic.main.selector_activity.*
import org.json.JSONArray
import org.json.JSONObject
import org.koin.android.ext.android.inject

/**
 * Activity that handles hamburger customization.
 */
class SelectorActivity : AppCompatActivity(), SelectorAdapterChangeListener, RequestListener {
    override fun onRequestResult(data: JSONArray) {}

    override fun onPutResult(data: JSONObject) {
        finish()
    }

    private val ingredients: Ingredients by inject()

    private lateinit var viewAdapter: SelectorAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var ingredientsMap: MutableMap<Int, Int> = HashMap()

    private var finalPrice = 0.0
    private var basePrice = 0.0

    private lateinit var data: HamburgerData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selector_activity)
        val bundle = this.intent.extras
        data = bundle?.getSerializable("item") as HamburgerData
        setData()
        add_button.setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("id_sandwich", data.id)
            jsonObject.put("extras", getExtraIngredient())
            DataProvider(this).put(this, "$ORDER_API${data.id}", jsonObject)
        }
    }

    private fun getExtraIngredient(): ArrayList<Int>{
        val list = arrayListOf<Int>()
        for (index in ingredientsMap.keys) {
            ingredientsMap[index]?.let { if (it != 0) {
                list.add(index)
                list.add(it)
            } }
        }
        Log.d(TAG, "getExtraIngredient: $list")
        return list
    }

    private fun setData() {
        basePrice = ingredients.calculateFullPrice(data.ingredients)
        setName(data.name)
        setPhoto(data.imageLink)
        setIngredients(data.ingredients)
        setPrice(basePrice)
        setSelectorAdapter()
    }

    private fun setName(name: String) {
        selection_name.text = name
    }

    private fun setPhoto(url: String) {
        selection_photo.setDefaultImageResId(R.mipmap.ic_launcher)
        selection_photo.setImageUrl(url, RequestController.getInstance(this).imageLoader)
    }

    private fun setIngredients(values: ArrayList<Int>) {
        selection_ingredients.text = ingredients.parseIngredientsList(values)
        for (ingredient in ingredients.getIngredients()) {
            ingredientsMap[ingredient.id] = 0
        }
    }

    private fun setPrice(value: Double) {
        selection_price.text = String.format(PRICE_FORMAT, value)
    }

    private fun setSelectorAdapter() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = SelectorAdapter(ingredients.getIngredients())
        selection_ingredient_list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        viewAdapter.setListener(this)
    }

    private fun calculateFinalPrice(): Double {
        finalPrice = basePrice
        for (index in ingredients.getIngredientsIdList()) {
            finalPrice += ingredients.calculatePrice(index, ingredientsMap[index] ?: 0)
        }
        Log.d(TAG, "calculateFinalPrice - basePrice: $basePrice finalPrice: $finalPrice")
        return finalPrice
    }

    override fun onSelectorAdapterChanged(value: Int, position: Int) {
        Log.d(TAG, "onSelectorAdapterChanged - value: $value position: $position")
        ingredientsMap[position] = value
        setPrice(calculateFinalPrice())
    }

    companion object {
        const val PRICE_FORMAT = "R$%.2f"
        private val TAG = Logger.tag
        private const val ORDER_API = DataProvider.SERVER_ADDRESS + "pedido/"
    }
}