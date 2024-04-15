package com.rafi.okegasfood.data.source.network.services

import com.rafi.okegasfood.BuildConfig
import com.rafi.okegasfood.data.source.network.model.CategoriesResponse
import com.rafi.okegasfood.data.source.network.model.MenuResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface OkeGasFoodApiService {

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @GET("listmenu")
    suspend fun getMenu(@Query("c") category: String? = null): MenuResponse

    companion object {
        @JvmStatic
        operator fun invoke(): OkeGasFoodApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(OkeGasFoodApiService::class.java)
        }
    }
}