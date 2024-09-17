package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.GamesRepository
import com.devexperto.testingexpert.domain.VideoGame
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class GetPopularGamesUseCaseTest{

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
        val repository: GamesRepository = mock {
            onBlocking { games } doReturn flowOf(expectedGames)
        }
        val useCase = GetPopularGamesUseCase(repository)

        val games = runBlocking { useCase().first() }

        Assert.assertEquals(expectedGames, games)
    }
}