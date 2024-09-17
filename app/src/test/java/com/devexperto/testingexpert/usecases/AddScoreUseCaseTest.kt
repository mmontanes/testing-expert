package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.ScoreboardRepository
import com.devexperto.testingexpert.domain.Score
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.X
import com.devexperto.testingexpert.domain.move
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking
import java.util.Date


@RunWith(MockitoJUnitRunner::class)
class AddScoreUseCaseTest{
    @Test
    fun `when invoke is called, then call repository addScore`() {
        val boardWithWinnerX = TicTacToe()
            .move(0, 0)
            .move(0, 1)
            .move(1, 0)
            .move(1, 1)
            .move(2, 0)
        val repository : ScoreboardRepository = mock()
        val useCase = AddScoreUseCase(repository)

        runBlocking { useCase(boardWithWinnerX) }

        argumentCaptor<Score> {
            verifyBlocking(repository) { addScore(capture()) }
            Assert.assertEquals(X, firstValue.winner)
            Assert.assertEquals(5, firstValue.numberOfMoves)
        }
    }
}