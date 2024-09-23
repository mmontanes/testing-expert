package com.devexperto.testingexpert.ui.games

import app.cash.turbine.test
import com.devexperto.testingexpert.domain.VideoGame
import com.devexperto.testingexpert.testrules.CoroutineTestRule
import com.devexperto.testingexpert.ui.games.GamesViewModel.*
import com.devexperto.testingexpert.usecases.GetPopularGamesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class GamesViewModelTest {

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

        val getPopularGamesUseCase : GetPopularGamesUseCase = mockk()
            every { getPopularGamesUseCase() } returns flowOf(expectedGames)

        val viewModel = GamesViewModel(getPopularGamesUseCase)

        // Act & Assert
        viewModel.state.test{
            assertEquals(UiState(), awaitItem())

            viewModel.onUiReady()
            assertEquals(UiState(isLoading = true), awaitItem())
            // cancleAndIgnoreRemainingEvents()
            assertEquals(UiState(isLoading = false, games = expectedGames), awaitItem())
        }
    }
}