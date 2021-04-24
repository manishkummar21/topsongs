package com.example.myapplication.service

import com.example.myapplication.model.FeedResponse
import retrofit2.Response

interface ApiHelper {
  suspend fun getTopSongs():Response<FeedResponse>
}