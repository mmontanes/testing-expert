package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.BoardRepository
import com.devexperto.testingexpert.data.datasource.BoardLocalDataSource
import com.devexperto.testingexpert.data.datasource.BoardLocalDataSourceFake
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.X
import com.devexperto.testingexpert.domain.move
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MakeBoardMoveUseCaseIntegrationTest{

    @Test
    fun `when invoke is called, then call repository move`() {
        val boardLocalDataSource : BoardLocalDataSource = BoardLocalDataSourceFake()
        val repository = BoardRepository(boardLocalDataSource)
        val useCase = MakeBoardMoveUseCase(repository)

        val ticTacToe = runBlocking {
            useCase(0, 0)
            repository.board.first()
        }

        assertEquals(X, ticTacToe.board[0][0])
        assertEquals(TicTacToe().move(0,0), ticTacToe)
    }
}