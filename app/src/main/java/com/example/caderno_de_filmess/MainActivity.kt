package com.example.caderno_de_filmess

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.caderno_de_filmess.components.HorizontalMovieCard
import com.example.caderno_de_filmess.components.VerticalMovieCard
import com.example.caderno_de_filmess.databinding.ActivityMainBinding
import com.example.caderno_de_filmess.models.Movie
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var store: MoviesStore
    private val selectedGenres = mutableSetOf<String>()
    private var filteredMovies = listOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        store = MoviesStore(this)
        filteredMovies = Utils.getMovies(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            insets
        }

        loadMovies()
        loadGenres()
    }

    override fun onResume() {
        super.onResume()
        loadMyMovies()
    }

    fun loadGenres() {
        val genres = Utils.getAllGenres(this)

        genres.forEach { genre ->
            val chip = Chip(this).apply {
                text = genre.replaceFirstChar { it.uppercase() }
                isCheckable = true
                isClickable = true

                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedGenres.add(genre)
                    } else {
                        selectedGenres.remove(genre)
                    }

                    filterMovies()
                }
            }

            binding.genresChipGroup.addView(chip)
        }
    }

    fun filterMovies() {
        filteredMovies = if (selectedGenres.isEmpty()) {
            Utils.getMovies(this)
        } else {
            Utils.getMovies(this).filter { movie ->
                selectedGenres.all { selected ->
                    movie.genres.map { it.lowercase() }.contains(selected)
                }
            }
        }
        loadMovies()
        loadMyMovies()
    }

    fun loadMyMovies() {
        val myMoviesIds = store.getMyMovies()
        val movies = filteredMovies.filter {  myMoviesIds.contains(it.id) }

        binding.myMoviesContainer.removeAllViews()

        if (movies.isEmpty()) {
            binding.myMoviesTitle.visibility = View.GONE
            binding.myMoviesScroll.visibility = View.GONE
            return
        } else {
            binding.myMoviesTitle.visibility = View.VISIBLE
            binding.myMoviesScroll.visibility = View.VISIBLE
        }

        movies.forEach { movie ->
            val view = VerticalMovieCard(this)

            view.setData(
                movie.id,
                movie.title,
                movie.thumb,
                store.getRating(movie.id)
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            view.layoutParams = params

            binding.myMoviesContainer.addView(view)
        }
    }

    fun loadMovies() {
        binding.moviesContainer.removeAllViews()

        filteredMovies.forEach { movie ->
            val view = HorizontalMovieCard(this)

            view.setData(
                movie.id,
                movie.title,
                movie.description,
                movie.thumb
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            params.bottomMargin = 40
            view.layoutParams = params

            binding.moviesContainer.addView(view)
        }
    }
}