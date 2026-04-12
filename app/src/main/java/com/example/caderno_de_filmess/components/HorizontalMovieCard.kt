package com.example.caderno_de_filmess.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.caderno_de_filmess.R
import com.example.caderno_de_filmess.Utils
import com.example.caderno_de_filmess.databinding.HorizontalMovieCardBinding

class HorizontalMovieCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var id: Int? = null
    private val binding: HorizontalMovieCardBinding = HorizontalMovieCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setData(id: Int, titleText: String, descText: String, imageUrl: String) {
        this.id = id

        binding.title.text = titleText

        val trimmed = if (descText.length > 255) {
            descText.substring(0, 255) + "..."
        } else {
            descText
        }

        binding.description.text = trimmed

        Glide.with(context).load(imageUrl).centerCrop().into(binding.bannerImage)

        setOnClickListener {
            Utils.goToMovieDetail(context, id)
        }
    }
}
