package com.brunodiegom.lanchonete.server

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class RequestController constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: RequestController? = null

        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: RequestController(context).also {
                        INSTANCE = it
                    }
                }
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue, LruBitmapCache())
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}