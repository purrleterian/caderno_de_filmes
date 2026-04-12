package com.example.caderno_de_filmess

import android.content.Context
import androidx.core.content.edit

class MoviesStore(context: Context) {
    private val shared = context.getSharedPreferences("movies", Context.MODE_PRIVATE)

    fun updateRating(movieId: Int, rating: Float) {
        shared.edit { putFloat("RATING_MOVIE_$movieId", rating) }
    }

    fun getRating(movieId: Int): Float {
        return shared.getFloat("RATING_MOVIE_$movieId", 0f)
    }

    fun getMyMovies(): Set<Int> {
        return shared.getStringSet("MY_MOVIES", emptySet())?.map { it.toInt() }?.toSet() ?: emptySet()
    }

    fun addMovie(movieId: Int) {
        shared.edit {
            val movies = getMyMovies().toMutableSet()
            movies.add(movieId)
            putStringSet("MY_MOVIES", movies.map { it.toString() }.toSet())
        }
    }

    fun removeMovie(movieId: Int) {
        shared.edit {
            val movies = getMyMovies().toMutableSet()
            movies.remove(movieId)
            putStringSet("MY_MOVIES", movies.map { it.toString() }.toSet())
        }
    }

    fun isMovieAdded(movieId: Int): Boolean {
        return getMyMovies().contains(movieId)
    }
}