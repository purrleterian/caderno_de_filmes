package com.example.caderno_de_filmess.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.caderno_de_filmess.R
import com.example.caderno_de_filmess.Utils
import com.example.caderno_de_filmess.databinding.VerticalMovieCardBinding

class VerticalMovieCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var id: Int? = null
    private val binding: VerticalMovieCardBinding = VerticalMovieCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setData(id: Int, titleText: String, imageUrl: String, ratingValue: Float) {
        this.id = id
        binding.title.text = titleText
        binding.rating.rating = ratingValue

        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(binding.poster)

        setOnClickListener {
            Utils.goToMovieDetail(context, id)
        }
    }
}