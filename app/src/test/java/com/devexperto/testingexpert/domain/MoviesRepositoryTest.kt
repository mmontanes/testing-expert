package com.devexperto.testingexpert.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesRemoteDataSourceDummy: MoviesRemoteDataSource {
    override fun findPopularMovies(): List<Movie> {
        return emptyList()
    }
}

class MoviesLocalDataSourceStub: MoviesLocalDataSource {
    override fun isEmpty(): Boolean = false

    override fun saveAll(movies: List<Movie>) {
        // TODO: Not necessary for this test
    }

    override fun findAll(): List<Movie> = listOf(Movie(1, "The Shawshank Redemption"))
}

class MoviesRepositoryTest{

    @Test
    fun `by default use local data source if exists`(){
        val moviesLocalDataSource = MoviesLocalDataSourceStub()
        val moviesRemoteDataSource = MoviesRemoteDataSourceDummy()
        val moviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)

        val movies = moviesRepository.findAll()

        assertEquals(1, movies[0].id)
    }

}