package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.entities.SongWithImages

@Dao
interface SongsImageDao {

    @Transaction
    @Query("SELECT * FROM Songs")
    suspend fun getAllTopSongs(): List<SongWithImages>
}
