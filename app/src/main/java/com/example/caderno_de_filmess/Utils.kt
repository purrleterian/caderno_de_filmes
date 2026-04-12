package com.example.caderno_de_filmess

import android.content.Context
import android.content.Intent
import com.example.caderno_de_filmess.models.Movie
import com.google.gson.Gson

class Utils {
    companion object {
        fun stringFromAssets(context: Context, fileName: String): String {
            return context.assets.open(fileName).bufferedReader().use { it.readText() }
        }

        fun getMovies(context: Context): List<Movie> {
            val json = stringFromAssets(context, "movies.json")
            return Gson().fromJson(json, Array<Movie>::class.java).toList()
        }

        fun goToMovieDetail(context: Context, id: Int) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }

        fun getMovieById(context: Context, id: Int): Movie? {
            val movies = getMovies(context)
            return movies.find { it.id == id }
        }

        fun getAllGenres(context: Context): Set<String> {
            val movies = getMovies(context)

            val genres = mutableSetOf<String>()

            movies.forEach { movie ->
                movie.genres.forEach { genre ->
                    genres.add(genre.lowercase())
                }
            }

            return genres
        }
    }
}