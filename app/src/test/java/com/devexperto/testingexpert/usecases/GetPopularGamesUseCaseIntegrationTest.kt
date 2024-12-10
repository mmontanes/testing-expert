package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.GamesRepository
import com.devexperto.testingexpert.data.datasource.GamesRemoteDataSource
import com.devexperto.testingexpert.data.datasource.GamesRemoteDataSourceFake
import com.devexperto.testingexpert.domain.VideoGame
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPopularGamesUseCaseIntegrationTest{

    @Test
    fun `when invoke is called, then return games from repository`() {
        val expectedGames = listOf(
            VideoGame(
                1,
                "Colonization",
                5.0,
                "https://www.google.com",
                java.util.Date()
            )
        )
        val gamesRemoteDataSource : GamesRemoteDataSource = GamesRemoteDataSourceFake(expectedGames)
        val repository = GamesRepository(gamesRemoteDataSource)

        val useCase = GetPopularGamesUseCase(repository)
        val games = runBlocking { useCase().first() }

        assertEquals(expectedGames, games)
    }
}