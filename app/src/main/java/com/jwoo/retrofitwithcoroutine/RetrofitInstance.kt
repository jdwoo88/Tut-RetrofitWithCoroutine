package com.jwoo.retrofitwithcoroutine

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitInstance {

    companion object {
        val BASE_URL: String = "https://jsonplaceholder.typicode.com/"

        fun getRetrofitInstance(): Retrofit {

            val interceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
            }
            //                .connectTimeout(60, TimeUnit.SECONDS)
            //                .writeTimeout(60, TimeUnit.SECONDS)
            //                .readTimeout(60, TimeUnit.SECONDS)
            .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}