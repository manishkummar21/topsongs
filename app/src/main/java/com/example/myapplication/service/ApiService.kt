package com.example.myapplication.service

import com.example.myapplication.model.FeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("WebObjects/MZStoreServices.woa/ws/RSS/topsongs/{limit}/xml")
    suspend fun getTopSongs(@Path("limit") limit: Int = 20):Response<FeedResponse>

}