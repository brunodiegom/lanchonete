package com.brunodiegom.lanchonete.view.fragment

import android.app.AlertDialog
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
import com.brunodiegom.lanchonete.model.*
import com.brunodiegom.lanchonete.server.DataProvider
import com.brunodiegom.lanchonete.server.RequestListener
import com.brunodiegom.lanchonete.view.adapter.CartAdapter
import kotlinx.android.synthetic.main.cart_fragment.*
import org.json.JSONArray
import org.json.JSONObject
import org.koin.android.ext.android.inject

class CartFragment : Fragment(), RequestListener {

    private val ingredients: Ingredients by inject()
    private val offers: Offers by inject()

    private lateinit var orders: ArrayList<OrderData>
    private lateinit var hamburgers: ArrayList<HamburgerData>

    private lateinit var viewAdapter: CartAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var total = 0.0
    private var discount = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestOrders()
        total = 0.0
        discount = 0.0

        finish_button.setOnClickListener {
            lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(context)
            builder.setPositiveButton(R.string.ok, null)
            builder.setTitle(getAlertTitle(orders.isEmpty()))
            builder.setMessage(getAlertMessage(orders.isEmpty()))
            dialog = builder.create()
            dialog.show()
        }
    }

    private fun getAlertTitle(isEmpty: Boolean) = if (isEmpty) R.string.empty_cart else R.string.order_finished
    private fun getAlertMessage(isEmpty: Boolean) = if (isEmpty) R.string.empty_cart_message else R.string.order_finished_message

    override fun onRequestResult(data: JSONArray, url: String) {
        Log.d(TAG, "onRequestResult: ${data.length()}")
        if (url == Orders.ORDER_API) {
            orders = Orders(data).dataSet
            requestHamburgers()
        } else {
            hamburgers = Hamburgers(data).dataSet
            viewManager = LinearLayoutManager(context)
            viewAdapter = CartAdapter(orders, ingredients, hamburgers)
            cart_recycler_list.apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
            calculateTotal()
            calculateDiscount()
            calculateTotalToPay()
        }
    }

    private fun calculateTotal() {
        for (order in orders) {
            val hamburger = getHamburger(order.idHamburger)
            total += ingredients.calculateFullPrice(order.extras)
            hamburger?.ingredients?.let { total += ingredients.calculateFullPrice(it) }
        }
        total_value.text = String.format(PRICE_FORMAT, total)
    }

    private fun calculateDiscount() {
        for (order in orders) {
            val hamburger = getHamburger(order.idHamburger)
            val list = arrayListOf<Int>()
            list.addAll(order.extras)
            hamburger?.ingredients?.let { list.addAll(it) }

            discount += offers.treeToTwoOffer(list, ingredients, 3)
            discount += offers.treeToTwoOffer(list, ingredients, 5)

            if (offers.isLightOffer(list)) {
                discount += ingredients.calculateFullPrice(list) * 0.1
            }
        }
        discount_value.text = String.format(DISCOUNT_PRICE_FORMAT, discount)
    }

    private fun calculateTotalToPay() {
        total_to_pay_value.text = String.format(PRICE_FORMAT, total - discount)
    }

    private fun getHamburger(id: Int): HamburgerData? {
        for (hamburger in hamburgers) {
            if (hamburger.id == id) return hamburger
        }
        return null
    }


    override fun onPutResult(data: JSONObject) {}

    private fun requestOrders() = DataProvider(this).request(context, Orders.ORDER_API)
    private fun requestHamburgers() = DataProvider(this).request(context, Hamburgers.HAMBURGER_API)

    companion object {
        private val TAG = Logger.tag
        private const val PRICE_FORMAT = "R$%.2f"
        private const val DISCOUNT_PRICE_FORMAT = "-$PRICE_FORMAT"
    }
}