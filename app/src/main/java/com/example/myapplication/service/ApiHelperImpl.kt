package com.example.myapplication.service

import com.example.myapplication.model.FeedResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper
{
    override suspend fun getTopSongs(): Response<FeedResponse> {
       return apiService.getTopSongs()
    }
}