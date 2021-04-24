package com.example.myapplication.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Songs(
    @PrimaryKey val id: String,
    val title: String,
    val name: String,
    val category_name: String,
    val artist_name: String,
    val releaseDate: String,
    val audioLink: String,
    val duration: Long
)

@Entity
data class Images(
    @PrimaryKey val imageID: String,
    val songCreatorId: String,
    val height: Int,
    val imagelink: String
){
    constructor(height: Int) : this("", "", height, "")

    override fun equals(other: Any?): Boolean {
        when (other) {
            is Images -> {
                return this.height == other.height
            }
            else -> return false
        }
    }
}

data class SongWithImages(
    @Embedded val song: Songs,
    @Relation(
        parentColumn = "id",
        entityColumn = "songCreatorId"
    )
    val images: List<Images>
)
