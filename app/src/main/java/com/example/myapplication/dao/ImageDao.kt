package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.myapplication.entities.Images
@Dao
interface ImageDao {
    @Insert
    suspend fun insertAll(images: List<Images>)
}