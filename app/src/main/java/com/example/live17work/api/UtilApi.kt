package com.example.live17work.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface UtilApi {


    @GET("users?sort=stars")
    suspend fun getGitHubUsers(@Query("per_page") query: Int,
                       @Query("since") page: Int): List<GetUsersListData>


    companion object {
        fun create(): UtilApi {
            val client = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(UtilApi::class.java)
        }
    }


    @GET
    fun getGitHubUser(@Url url: String): Call<GetUserData>
}