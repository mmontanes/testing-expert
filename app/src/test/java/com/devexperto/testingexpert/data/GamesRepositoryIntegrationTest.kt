package com.devexperto.testingexpert.data

import com.devexperto.testingexpert.data.datasource.GamesRemoteDataSourceFake
import com.devexperto.testingexpert.domain.VideoGame
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Date


class GamesRepositoryIntegrationTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @Test
    fun `when getGames is called, return list of games from remote data source`() {
        val expectedGames = listOf(VideoGame(1,"Colonization",5.0,"https://www.google.com",Date()))
        val gamesRemoteDataSource = GamesRemoteDataSourceFake(expectedGames)
        val repository = GamesRepository(gamesRemoteDataSource)

        val games = runBlocking { repository.games.first() }

        assertEquals(expectedGames, games)
    }
}