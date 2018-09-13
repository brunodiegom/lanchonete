package com.brunodiegom.lanchonete.server

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.brunodiego.calculadoraanimal.component.Logger

class DataProvider(private var requestListener: RequestListener) {

    fun request(context: Context?, url: String) {
        val stringRequest = JsonArrayRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    Log.d(TAG, "Response: $response")
                    requestListener.onRequestResult(response)
                },
                Response.ErrorListener { Log.e(TAG, "Error on request") })
        context?.let { RequestController.getInstance(it).addToRequestQueue(stringRequest) }
    }

    companion object {
        private val TAG = Logger.tag
        const val SERVER_ADDRESS = "http://10.0.2.2:8080/api/"
    }
}