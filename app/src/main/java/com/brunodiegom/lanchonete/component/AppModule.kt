package com.brunodiegom.lanchonete.component

import com.brunodiegom.lanchonete.model.Ingredients
import com.brunodiegom.lanchonete.model.Offers
import org.koin.dsl.module.module

object AppModule {
    val appModule = module {
        single { Ingredients(get()) }
        single { Offers(get()) }
    }
}