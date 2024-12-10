package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.BoardRepository
import com.devexperto.testingexpert.data.datasource.BoardLocalDataSource
import com.devexperto.testingexpert.data.datasource.BoardLocalDataSourceFake
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.move
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetCurrentBoardUseCaseIntegrationTest {

    @Test
    fun `when invoke is called, then return board from repository`() {
        val expectedBoard = TicTacToe()
            .move(0,0)
            .move(1, 0)
            .move(0, 1)
            .move(1, 1)
        val localDataSource : BoardLocalDataSource = BoardLocalDataSourceFake(expectedBoard)
        val repository = BoardRepository(localDataSource)

        val useCase = GetCurrentBoardUseCase(repository)

        val board = runBlocking { useCase().first() }

        Assert.assertEquals(expectedBoard, board)
    }

}