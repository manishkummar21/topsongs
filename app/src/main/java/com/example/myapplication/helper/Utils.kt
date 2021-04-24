package com.example.myapplication.helper

import com.example.myapplication.entities.Images
import com.example.myapplication.model.Link
import com.example.myapplication.model.Songs
import kotlin.random.Random

object Utils {

    fun convertXMLModeltoEntity(songs: List<Songs>): Pair<List<com.example.myapplication.entities.Songs>, List<Images>> {
        val topSongs: ArrayList<com.example.myapplication.entities.Songs> = arrayListOf()
        val song_images: ArrayList<Images> = arrayListOf()
        songs.forEach {
            val audioLink = it.link.get(it.link.indexOf(Link("audio/x-m4a")))
            val item = com.example.myapplication.entities.Songs(
                it.id?.id.toNonNull(),
                it.title.toNonNull(),
                it.name.toNonNull(),
                it.category?.label.toNonNull(),
                it.artist.toNonNull(),
                it.releaseDate?.label.toNonNull(),
                audioLink.href.toNonNull(),
                audioLink.duration
            )
            it.images.forEach {
                song_images.add(
                    Images(
                        imageID = item.id + Random.nextInt(),
                        songCreatorId = item.id,
                        height = it.height,
                        imagelink = it.text.toNonNull()
                    )
                )
            }
            topSongs.add(item)
        }
        return Pair(topSongs, song_images)
    }


    fun String?.toNonNull(): String {
        return this ?: ""
    }


    fun convertSecondsToMmSs(milliSec: Long): String {
        val s = (milliSec / 1000) % 60
        val m = ((milliSec / 1000) / 60) % 60
        return String.format("%02d:%02d", m, s)
    }
}