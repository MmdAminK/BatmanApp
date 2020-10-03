package com.app.batman.services.retrofit_builder

import com.app.batman.services.GetFilmsWebServices


object ApiServices {

    val getFilmsApiService: GetFilmsWebServices by lazy {
        RetrofitInstance.customerRetrofitBuilder
            .build()
            .create(GetFilmsWebServices::class.java)
    }
}