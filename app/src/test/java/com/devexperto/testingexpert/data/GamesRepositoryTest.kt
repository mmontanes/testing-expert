package com.devexperto.testingexpert.data

import com.devexperto.testingexpert.data.datasource.GamesRemoteDataSource
import com.devexperto.testingexpert.domain.VideoGame
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GamesRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @Test
    fun `when getGames is called, return list of games from remote data source`() {
        val expectedGames = listOf(
            VideoGame(
                1,
                "Colonization",
                5.0,
                "https://www.google.com",
                Date()
            )
        )
        val remoteDataSource : GamesRemoteDataSource = mockk() {
            coEvery { getGames() } returns  expectedGames
        }

        val repository = GamesRepository(remoteDataSource)

        val games = runBlocking { repository.games.first() }

        assertEquals(expectedGames, games)
    }
}