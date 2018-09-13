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
import com.brunodiegom.lanchonete.model.Hamburgers
import com.brunodiegom.lanchonete.model.Ingredients
import com.brunodiegom.lanchonete.server.DataProvider
import com.brunodiegom.lanchonete.server.RequestListener
import com.brunodiegom.lanchonete.view.activity.adapter.HamburgerAdapter
import kotlinx.android.synthetic.main.menu_fragment.*
import org.json.JSONArray
import org.koin.android.ext.android.inject

class MenuFragment : Fragment(), RequestListener {

    private val ingredients: Ingredients by inject()

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestDataSet()
    }

    private fun requestDataSet() = DataProvider(this).request(context, Hamburgers.HAMBURGER_API)

    override fun onRequestResult(data: JSONArray) {
        Log.d(TAG, "onRequestResult: ${data.length()}")
        viewManager = LinearLayoutManager(context)
        viewAdapter = HamburgerAdapter(Hamburgers(data).dataSet, ingredients)
        menu_recycler_list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    companion object {
        private val TAG = Logger.tag
    }
}