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
import kotlin.math.PI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var store: MoviesStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        store = MoviesStore(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            insets
        }

        loadMovies()
    }

    override fun onResume() {
        super.onResume()
        binding.myMoviesContainer.removeAllViews()
        loadMyMovies()
    }

    fun loadMyMovies() {
        val movies = Utils.getMoviesByIds(this, store.getMyMovies());

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
        val movies = Utils.getMovies(this)

        movies.forEach { movie ->
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