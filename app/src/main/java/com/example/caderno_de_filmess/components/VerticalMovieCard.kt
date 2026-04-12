package com.example.caderno_de_filmess.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.caderno_de_filmess.R
import com.example.caderno_de_filmess.Utils

class VerticalMovieCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var id: Int? = null
    private val title: TextView
    private val image: ImageView
    private val rating: RatingBar

    init {
        inflate(context, R.layout.vertical_movie_card, this)

        title = findViewById(R.id.title)
        image = findViewById(R.id.poster)
        rating = findViewById(R.id.rating)
    }

    fun setData(id: Int, titleText: String, imageUrl: String, ratingValue: Float) {
        this.id = id
        title.text = titleText
        rating.rating = ratingValue

        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(image)

        setOnClickListener {
            Utils.goToMovieDetail(context, id)
        }
    }
}