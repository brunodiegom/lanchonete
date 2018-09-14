package com.brunodiegom.lanchonete.server

import org.json.JSONArray
import org.json.JSONObject

interface RequestListener {
    fun onRequestResult(data: JSONArray, url: String)
    fun onPutResult(data: JSONObject)
}