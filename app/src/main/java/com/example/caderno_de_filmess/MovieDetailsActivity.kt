package com.example.caderno_de_filmess

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.caderno_de_filmess.databinding.ActivityMovieDetailsBinding
import com.example.caderno_de_filmess.models.Movie

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movie: Movie
    private lateinit var store: MoviesStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        store = MoviesStore(this)

        val movieId = intent.getIntExtra("id", 0)

        if (movieId != 0) {
            movie = Utils.getMovieById(this, movieId) ?: return
            bindMovie()
        } else {
            Toast.makeText(this, "Filme não encontrado!", Toast.LENGTH_SHORT).show()
            back()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.movie_details)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.topBar.setNavigationOnClickListener {
            back()
        }

        bindUpdateRating()
        bindWatched()
    }

    private fun bindUpdateRating() {
        binding.rating.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (!fromUser) return@setOnRatingBarChangeListener

            store.updateRating(movie.id, rating)

            if (rating != 0f) {
                store.addMovie(movie.id)
                binding.watchedCheckbox.isChecked = true
            }

            Toast.makeText(this, "Nova avaliação: $rating", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindWatched() {
        binding.watchedCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                store.addMovie(movie.id)
            } else {
                store.removeMovie(movie.id)
            }
        }
    }

    private fun back() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun bindMovie() {
        Glide.with(this)
            .load(movie.thumb)
            .centerCrop()
            .into(binding.banner)

        binding.title.text = movie.title
        binding.description.text = movie.description
        binding.rating.rating = store.getRating(movie.id)
        binding.watchedCheckbox.isChecked = store.isMovieAdded(movie.id)

    }
}