package com.brunodiegom.lanchonete.server

import org.json.JSONArray

interface RequestListener {
    fun onRequestResult(data: JSONArray)
}