package com.devexperto.testingexpert.ui.board

import app.cash.turbine.test
import com.devexperto.testingexpert.data.BoardRepository
import com.devexperto.testingexpert.data.ScoreboardRepository
import com.devexperto.testingexpert.data.datasource.BoardLocalDataSource
import com.devexperto.testingexpert.data.datasource.BoardLocalDataSourceFake
import com.devexperto.testingexpert.data.datasource.ScoreLocalDataSource
import com.devexperto.testingexpert.data.datasource.ScoreLocalDataSourceFake
import com.devexperto.testingexpert.domain.GameState
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.move
import com.devexperto.testingexpert.testrules.CoroutineTestRule
import com.devexperto.testingexpert.ui.board.BoardViewModel.*
import com.devexperto.testingexpert.usecases.AddScoreUseCase
import com.devexperto.testingexpert.usecases.AddScoreUseCase_Factory
import com.devexperto.testingexpert.usecases.GetCurrentBoardUseCase
import com.devexperto.testingexpert.usecases.MakeBoardMoveUseCase
import com.devexperto.testingexpert.usecases.ResetBoardUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BoardViewModelIntegrationTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel : BoardViewModel

    @Before
    fun setUp() {
        val boardRepository = BoardRepository(BoardLocalDataSourceFake())
        val scoreBoardRepository = ScoreboardRepository(ScoreLocalDataSourceFake())

        viewModel = BoardViewModel(
            makeBoardMoveUseCase = MakeBoardMoveUseCase(boardRepository),
            getCurrentBoardUseCase = GetCurrentBoardUseCase(boardRepository),
            addScoreUseCase = AddScoreUseCase(scoreBoardRepository),
            resetBoardUseCase = ResetBoardUseCase(boardRepository)
        )
    }

    @Test
    fun `at the beginning, the game is not started`() = runTest {
        viewModel.state.test{
            assertEquals(UiState(gameState = GameState.NotStarted), awaitItem())
        }
    }

    @Test
    fun `when start game is called, game state is in progress`() = runTest {
        viewModel.state.test{
            assertEquals(UiState(gameState = GameState.NotStarted), awaitItem())

            viewModel.startGame()
            assertEquals(UiState(gameState = GameState.InProgress), awaitItem())
        }
    }

    @Test
    fun `when reset is called, then the game is cleared`() = runTest {
        viewModel.startGame()

        viewModel.move(0,1)
        viewModel.resetGame()

        runCurrent()

        assertEquals(
            UiState(TicTacToe(), GameState.InProgress),
            viewModel.state.value
        )
    }

    @Test
    fun `move updates the state`() = runTest {
        viewModel.startGame()

        viewModel.move(0,1)
        runCurrent()

        assertEquals(
            UiState(TicTacToe().move(0,1), GameState.InProgress),
            viewModel.state.value
        )
    }
}