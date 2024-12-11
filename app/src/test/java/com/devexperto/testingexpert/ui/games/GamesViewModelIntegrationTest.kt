package com.devexperto.testingexpert.ui.games

import app.cash.turbine.test
import com.devexperto.testingexpert.data.GamesRepository
import com.devexperto.testingexpert.data.datasource.GamesRemoteDataSourceFake
import com.devexperto.testingexpert.domain.VideoGame
import com.devexperto.testingexpert.testrules.CoroutineTestRule
import com.devexperto.testingexpert.ui.games.GamesViewModel.UiState
import com.devexperto.testingexpert.usecases.GetPopularGamesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Date

class GamesViewModelIntegrationTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `when UI is ready, the call get games`() = runTest {
        // Arrange
        val expectedGames = listOf(
            VideoGame(
                id = 1,
                name = "Super Mario Bros",
                rating = 4.5,
                imageUrl = "1985-09-13",
                releaseDate = Date()
            )
        )
        val gamesRemoteDataSource = GamesRemoteDataSourceFake(expectedGames)
        val gamesRepository = GamesRepository(gamesRemoteDataSource)
        val getPopularGamesUseCase = GetPopularGamesUseCase(gamesRepository)
        val viewModel = GamesViewModel(getPopularGamesUseCase)

        // Act & Assert
        viewModel.state.test{
            assertEquals(UiState(), awaitItem())

            viewModel.onUiReady()
            assertEquals(UiState(isLoading = true), awaitItem())
            assertEquals(UiState(games = expectedGames), awaitItem())
        }
    }
}