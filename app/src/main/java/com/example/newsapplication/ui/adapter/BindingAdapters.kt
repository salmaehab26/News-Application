package com.example.newsapplication.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsapplication.R


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.news_en_1920x1080)
        .error(R.drawable.ic_launcher_background)
        .into(view)
}