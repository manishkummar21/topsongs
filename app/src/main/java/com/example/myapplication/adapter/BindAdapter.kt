package com.example.myapplication.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.entities.Images

object BindAdapter {

    @JvmStatic
    @BindingAdapter(value = ["setImageUrl"])
    fun AppCompatImageView.bindImageUrl(url: List<Images>) {
        val imageLink = url[url.indexOf(Images(height = 170))].imagelink // i took the max height of 170
        if (imageLink.isNotBlank()) {
            Glide.with(this.context)
                .load(imageLink)
                .transform(CenterCrop(), RoundedCorners(24))
                .into(this)
        }
    }
}