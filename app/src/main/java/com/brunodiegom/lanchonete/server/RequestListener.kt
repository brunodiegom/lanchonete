package com.brunodiegom.lanchonete.server

import org.json.JSONArray
import org.json.JSONObject

interface RequestListener {
    fun onRequestResult(data: JSONArray)
    fun onPutResult(data: JSONObject)
}