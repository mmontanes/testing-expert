package com.devexperto.testingexpert.domain

data class Movie(val id: Int, val title: String)

class MoviesRepository(
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) {

    fun findAll(): List<Movie> {
        if (moviesLocalDataSource.isEmpty()) {
            val movies = moviesRemoteDataSource.findPopularMovies()
            moviesLocalDataSource.saveAll(movies)
        }
        return moviesLocalDataSource.findAll()
    }

}

interface MoviesLocalDataSource {
    fun isEmpty(): Boolean
    fun saveAll(movies: List<Movie>)
    fun findAll(): List<Movie>
}

interface MoviesRemoteDataSource {
    fun findPopularMovies(): List<Movie>
}