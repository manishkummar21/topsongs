package com.example.myapplication

import android.util.Log
import com.example.myapplication.entities.SongWithImages
import com.example.myapplication.helper.ResultWrapper
import com.example.myapplication.helper.Utils
import com.example.myapplication.service.ApiHelper
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class MainRepo @Inject constructor(
    private val apiHelper: ApiHelper,
    private val appDatabase: AppDatabase
) {

    fun getTopOnlineSongs() = flow {
        val top_songs = apiHelper.getTopSongs()
        top_songs.body()?.let {
            val convertedModel = Utils.convertXMLModeltoEntity(it.entry)
            appDatabase.songsdao().insertAll(convertedModel.first)
            appDatabase.imagedao().insertAll(convertedModel.second)
            emit(appDatabase.songwithimagedao().getAllTopSongs())
        } ?: throw Exception("Something went Wrong")
    }

    fun getTopDBSongs() = flow {
        val result = appDatabase.songwithimagedao().getAllTopSongs()
        if (result.isNotEmpty())
            emit(result)
    }
}