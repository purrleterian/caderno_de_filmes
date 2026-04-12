package com.example.caderno_de_filmess.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.caderno_de_filmess.R
import com.example.caderno_de_filmess.Utils

class HorizontalMovieCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var id: Int? = null
    private val title: TextView
    private val description: TextView
    private val image: ImageView

    init {
        inflate(context, R.layout.horizontal_movie_card, this)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        image = findViewById(R.id.bannerImage)

    }

    fun setData(id: Int, titleText: String, descText: String, imageUrl: String) {
        this.id = id
        title.text = titleText

        val trimmed = if (descText.length > 255) {
            descText.substring(0, 255) + "..."
        } else {
            descText
        }

        description.text = trimmed

        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(image)

        setOnClickListener {
            Utils.goToMovieDetail(context, id)
        }
    }
}
