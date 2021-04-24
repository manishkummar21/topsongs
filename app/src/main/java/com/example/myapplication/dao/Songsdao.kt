package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.myapplication.entities.Songs

@Dao
interface Songsdao {
    @Insert
    suspend fun insertAll(songs: List<Songs>)
}