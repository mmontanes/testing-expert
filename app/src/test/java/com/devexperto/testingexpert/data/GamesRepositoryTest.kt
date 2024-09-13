package com.devexperto.testingexpert.data

import com.devexperto.testingexpert.data.datasource.GamesRemoteDataSource
import com.devexperto.testingexpert.domain.VideoGame
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import org.mockito.kotlin.wheneverBlocking
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GamesRepositoryTest {


    // Test con anotaciones de Mockito
    @Mock
    lateinit var gamesRemoteDataSource: GamesRemoteDataSource
    private lateinit var repository: GamesRepository
    private val expectedVideoGames = listOf(VideoGame(
        1,
        "Colonization",
        5.0,
        "https://www.google.com",
          Date()))

    @Before
    fun setUp() {
        wheneverBlocking { gamesRemoteDataSource.getGames() }.thenReturn(expectedVideoGames)
        repository = GamesRepository(gamesRemoteDataSource)
    }

    @Test
    fun `when getGames is called, return list of games from remote data source`() {
        val games = runBlocking { repository.games.first() }

        assertEquals(expectedVideoGames, games)
    }

    // Test usando Mockito DSL
//    @Test
//    fun `when getGames is called, return list of games from remote data source`() {
//        val expectedGames = listOf(
//            VideoGame(
//                1,
//                "Colonization",
//                5.0,
//                "https://www.google.com",
//                Date()
//            )
//        )
//        val remoteDataSource = mock<GamesRemoteDataSource> {
//            onBlocking { getGames() } doReturn  expectedGames
//        }
//        val repository = GamesRepository(remoteDataSource)
//
//        val games = runBlocking { repository.games.first() }
//
//        assertEquals(expectedGames, games)
//
//    }

}