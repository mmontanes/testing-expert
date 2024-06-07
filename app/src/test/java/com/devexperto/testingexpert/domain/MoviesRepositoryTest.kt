package com.devexperto.testingexpert.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesRemoteDataSourceStub: MoviesRemoteDataSource {
    override fun findPopularMovies(): List<Movie> = listOf(
        Movie(1, "Movie 1"),
        Movie(2, "Movie 2")
    )
}

class MoviesLocalDataSourceSpy: MoviesLocalDataSource {
    private val moviesDataSource = mutableListOf<Movie>()
    var saveAllCalled = false

    override fun isEmpty(): Boolean = moviesDataSource.isEmpty()

    override fun saveAll(movies: List<Movie>) {
        saveAllCalled = true
        moviesDataSource.addAll(movies)
    }

    override fun findAll(): List<Movie> = moviesDataSource
}

class MoviesRepositoryTest{

    @Test
    fun `by default use local data source if exists`(){
        val moviesLocalDataSource = MoviesLocalDataSourceSpy()
        moviesLocalDataSource.saveAll(listOf(Movie(1, "Movie 1")))
        val moviesRemoteDataSource = MoviesRemoteDataSourceStub()
        val moviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)

        val movies = moviesRepository.findAll()

        assertEquals(1, movies.size)
    }

    @Test
    fun `if local data source is empty, use remote data source`(){
        val moviesLocalDataSource = MoviesLocalDataSourceSpy()
        val moviesRemoteDataSource = MoviesRemoteDataSourceStub()
        val moviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)

        val movies = moviesRepository.findAll()

        assertEquals(2, movies.size)
    }

    @Test
    fun `if local data source is empty, save remote data source in local data source`(){
        val moviesLocalDataSource = MoviesLocalDataSourceSpy()
        val moviesRemoteDataSource = MoviesRemoteDataSourceStub()
        val moviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)

        moviesRepository.findAll()

        assertEquals(true, moviesLocalDataSource.saveAllCalled)
    }
}