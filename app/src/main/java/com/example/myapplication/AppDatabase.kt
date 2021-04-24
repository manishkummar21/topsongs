package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.dao.ImageDao
import com.example.myapplication.dao.SongsImageDao
import com.example.myapplication.dao.Songsdao
import com.example.myapplication.entities.Images
import com.example.myapplication.entities.Songs


@Database(entities = arrayOf(Songs::class, Images::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songsdao(): Songsdao
    abstract fun imagedao(): ImageDao
    abstract fun songwithimagedao(): SongsImageDao
}