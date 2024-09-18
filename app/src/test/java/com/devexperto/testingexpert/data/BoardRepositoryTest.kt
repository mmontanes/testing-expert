package com.devexperto.testingexpert.data

import com.devexperto.testingexpert.data.datasource.BoardLocalDataSource
import com.devexperto.testingexpert.domain.TicTacToe
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class BoardRepositoryTest {

    private val expectedBoard = TicTacToe()

    @get:Rule
    val mockkRule = MockKRule(this)

    // Lo de pasarle una lambda es para poder hacer un mock con comportamiento
    private fun boardLocalDataSourceMock(body: BoardLocalDataSource.() -> Unit = {})
    : BoardLocalDataSource  = mockk {
        every { board } returns flowOf(expectedBoard)
        body()
    }

     @Test
     fun `when board is requested, do it from local data source`() {
         // Arrange
        val boardLocalDataSourceMock: BoardLocalDataSource = boardLocalDataSourceMock()
        val boardRepository = BoardRepository(boardLocalDataSourceMock)
         // Act
         val board = runBlocking { boardRepository.board.first() }
         // Assert
         assertEquals(expectedBoard, board)
     }

    @Test
    fun `when move is called, store row and column in local data source`() {
        val boardLocalDataSourceMock: BoardLocalDataSource = boardLocalDataSourceMock(){
            coJustRun { saveMove(any(), any()) }
        }

        val boardRepository = BoardRepository(boardLocalDataSourceMock)

        runBlocking { boardRepository.move(0, 0) }

        coVerify { boardLocalDataSourceMock.saveMove(0,0) }
    }

    @Test
    fun `when reset is called`() {
        val boardLocalDataSource: BoardLocalDataSource = mockk {
            every { board } returns  flowOf(expectedBoard)
            coJustRun { reset() }
        }
        val boardRepository = BoardRepository(boardLocalDataSource)

        runBlocking { boardRepository.reset() }

        coVerify { boardLocalDataSource.reset() }
    }
}