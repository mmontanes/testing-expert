package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.BoardRepository
import com.devexperto.testingexpert.data.datasource.BoardLocalDataSourceFake
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.move
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class ResetBoardUseCaseIntegrationTest {

    @Test
    fun `when invoke is called, then call repository reset`() {
        val initialBoard = TicTacToe()
            .move(0, 0)
            .move(0, 1)
        val boardLocalDataSource = BoardLocalDataSourceFake(initialBoard)
        val repository = BoardRepository(boardLocalDataSource)
        val useCase = ResetBoardUseCase(repository)

        val ticTacToe =runBlocking {
            useCase()
            repository.board.first()
        }

        assertEquals(TicTacToe(), ticTacToe)
    }
}