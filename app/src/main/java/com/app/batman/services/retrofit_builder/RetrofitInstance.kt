package com.app.batman.services.retrofit_builder

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    private const val ipAddress = "http://omdbapi.com/"
    private val httpClient = OkHttpClient.Builder()


    init {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logger)
        httpClient
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
    }

    val customerRetrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(ipAddress)
            .client(httpClient.build())
            .addConverterFactory(JacksonConverterFactory.create())
    }
}