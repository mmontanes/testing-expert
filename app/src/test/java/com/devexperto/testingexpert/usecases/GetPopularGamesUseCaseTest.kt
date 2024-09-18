package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.GamesRepository
import com.devexperto.testingexpert.domain.VideoGame
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GetPopularGamesUseCaseTest{

    @get:Rule
    val mockkRule = MockKRule(this)

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
        val repository: GamesRepository = mockk {
            coEvery { games } returns flowOf(expectedGames)
        }
        val useCase = GetPopularGamesUseCase(repository)

        val games = runBlocking { useCase().first() }

        Assert.assertEquals(expectedGames, games)
    }
}