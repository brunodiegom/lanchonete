package com.brunodiegom.lanchonete.model

import java.io.Serializable

data class HamburgerData(val id: Int, val name: String, val ingredients: ArrayList<Int>, val imageLink: String, val extras: ArrayList<Int>? = null): Serializable