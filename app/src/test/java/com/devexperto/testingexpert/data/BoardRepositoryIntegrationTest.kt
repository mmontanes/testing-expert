package com.devexperto.testingexpert.data

import com.devexperto.testingexpert.data.datasource.BoardLocalDataSourceFake
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.move
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class BoardRepositoryIntegrationTest {

     @Test
     fun `when board is requested, do it from local data source`() {
         // Arrange
         val expectedBoard = TicTacToe().move(0,0).move(1, 1)
         val localDataSource= BoardLocalDataSourceFake(expectedBoard)
         val boardRepository = BoardRepository(localDataSource)
         // Act
         val board = runBlocking { boardRepository.board.first() }
         // Assert
         assertEquals(expectedBoard, board)
     }

    @Test
    fun `when move is called, store row and column in local data source`() {
         val localDataSource= BoardLocalDataSourceFake(TicTacToe())
         val boardRepository = BoardRepository(localDataSource)

         runBlocking { boardRepository.move(0, 0) }
         val board = runBlocking { boardRepository.board.first() }
         // Assert
         assertEquals(TicTacToe().move(0,0), board)
    }

    @Test
    fun `when reset is called`() {
        val expectedBoard = TicTacToe().move(0,0).move(1, 1)
        val localDataSource= BoardLocalDataSourceFake(expectedBoard)
        val boardRepository = BoardRepository(localDataSource)

        runBlocking { boardRepository.reset() }
        val board = runBlocking { boardRepository.board.first() }

        assertEquals(TicTacToe(), board)

    }
}