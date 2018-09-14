package com.brunodiegom.lanchonete.model

import java.io.Serializable

data class OrderData(val id: Int, val idHamburger: Int, val extras: ArrayList<Int>): Serializable