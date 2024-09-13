package com.devexperto.testingexpert.data

import com.devexperto.testingexpert.data.datasource.BoardLocalDataSource
import com.devexperto.testingexpert.domain.TicTacToe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking


@RunWith(MockitoJUnitRunner::class)
class BoardRepositoryTest {
    // Test usando Mockito DSL
     @Test
     fun `when board is requested, do it from local data source`() {
         // Arrange
         val expectedBoard = TicTacToe()
         val localDataSource = mock <BoardLocalDataSource> {
             on { board } doReturn  flowOf(expectedBoard)
         }
         val boardRepository = BoardRepository(localDataSource)
         // Act
         val board = runBlocking { boardRepository.board.first() }
         // Assert
         assertEquals(expectedBoard, board)
     }

    @Test
    fun `when move is called, store row and column in local data source`() {
        val boardLocalDataSource: BoardLocalDataSource = mock()
        val boardRepository = BoardRepository(boardLocalDataSource)

        runBlocking { boardRepository.move(0, 0) }

        verifyBlocking(boardLocalDataSource) { saveMove(0,0)}
    }

    @Test
    fun `when reset is called`() {
        val boardLocalDataSource: BoardLocalDataSource = mock()
        val boardRepository = BoardRepository(boardLocalDataSource)

        runBlocking { boardRepository.reset() }

        verifyBlocking(boardLocalDataSource) { reset()}
    }
}