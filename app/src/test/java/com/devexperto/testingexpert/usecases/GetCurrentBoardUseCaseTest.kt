package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.BoardRepository
import com.devexperto.testingexpert.domain.TicTacToe
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GetCurrentBoardUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @Test
    fun `when invoke is called, then return board from repository`() {
        val expectedBoard = TicTacToe()
        val repository: BoardRepository = mockk() {
            coEvery { board } returns  flowOf(expectedBoard)
        }
        val useCase = GetCurrentBoardUseCase(repository)

        val board = runBlocking { useCase().first() }

        Assert.assertEquals(expectedBoard, board)
    }

}