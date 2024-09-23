package com.devexperto.testingexpert.ui.board

import com.devexperto.testingexpert.domain.GameState
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.testrules.CoroutineTestRule
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
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BoardViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `at the beginning, the game is not started`() = runTest {
        val viewModel = BoardViewModel(
            makeBoardMoveUseCase = mockk(),
            getCurrentBoardUseCase = mockk(),
            addScoreUseCase = mockk(),
            resetBoardUseCase = mockk()
        )

        // Ambas aserciones son equivalentes
        assertEquals(BoardViewModel.UiState(gameState = GameState.NotStarted), viewModel.state.value)
        assertEquals(GameState.NotStarted, viewModel.state.value.gameState)
    }

    @Test
    fun `when start game is called, game state is in progress`() = runTest {
        val getCurrentBoardUseCase : GetCurrentBoardUseCase = mockk()
            every { getCurrentBoardUseCase() } returns flowOf(TicTacToe())

        val viewModel = BoardViewModel(
            makeBoardMoveUseCase = mockk(),
            getCurrentBoardUseCase = getCurrentBoardUseCase,
            addScoreUseCase = mockk(),
            resetBoardUseCase = mockk()
        )

        viewModel.startGame()

        runCurrent()

        // Ambas aserciones son equivalentes
        assertEquals(BoardViewModel.UiState(gameState = GameState.InProgress), viewModel.state.value)
        assertEquals(GameState.InProgress, viewModel.state.value.gameState)
    }

    @Test
    fun `when reset is called, then the game is cleared`() = runTest {
        val getCurrentBoardUseCase : GetCurrentBoardUseCase = mockk()
            every { getCurrentBoardUseCase() } returns flowOf(TicTacToe())
        val resetBoardUseCase : ResetBoardUseCase = mockk()
        val viewModel = BoardViewModel(
            makeBoardMoveUseCase = mockk(),
            getCurrentBoardUseCase = getCurrentBoardUseCase,
            addScoreUseCase = mockk(),
            resetBoardUseCase = resetBoardUseCase
        )

        coJustRun { resetBoardUseCase() }
        viewModel.resetGame()
        runCurrent()

        coVerify { resetBoardUseCase() }
    }

    @Test
    fun `move is recorded by use case`() = runTest {
        val getCurrentBoardUseCase : GetCurrentBoardUseCase = mockk()
            every { getCurrentBoardUseCase() } returns flowOf(TicTacToe())
        val makeBoardMoveUseCase : MakeBoardMoveUseCase = mockk()
        val viewModel = BoardViewModel(
            makeBoardMoveUseCase = makeBoardMoveUseCase,
            getCurrentBoardUseCase = getCurrentBoardUseCase,
            addScoreUseCase = mockk(),
            resetBoardUseCase = mockk()
        )
        coJustRun { makeBoardMoveUseCase(0, 0) }

        viewModel.move(0, 0)
        runCurrent()

        coVerify { makeBoardMoveUseCase(0, 0) }
    }
}