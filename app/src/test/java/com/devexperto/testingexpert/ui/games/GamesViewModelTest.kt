package com.devexperto.testingexpert.ui.games

import com.devexperto.testingexpert.domain.VideoGame
import com.devexperto.testingexpert.testrules.CoroutineTestRule
import com.devexperto.testingexpert.usecases.GetPopularGamesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
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
            every { getPopularGamesUseCase.invoke() } returns flow {
                delay(2000)
                emit(expectedGames)
            }

        val viewModel = GamesViewModel(getPopularGamesUseCase)

        // Act
        viewModel.onUiReady()

        // Assert
        assertEquals(GamesViewModel.UiState(), viewModel.state.value)
        advanceTimeBy(500)
        assertEquals(GamesViewModel.UiState(isLoading = true), viewModel.state.value)
        advanceTimeBy(1600)
        assertEquals(GamesViewModel.UiState(games = expectedGames, isLoading = false), viewModel.state.value)
    }
}