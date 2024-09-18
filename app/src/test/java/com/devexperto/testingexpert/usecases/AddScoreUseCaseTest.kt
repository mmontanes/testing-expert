package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.ScoreboardRepository
import com.devexperto.testingexpert.domain.Score
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.X
import com.devexperto.testingexpert.domain.move
import io.mockk.coJustRun
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class AddScoreUseCaseTest{

    @get:Rule
    val mockkRule = MockKRule(this)

    @Test
    fun `when invoke is called, then call repository addScore`() {
        val boardWithWinnerX = TicTacToe()
            .move(0, 0)
            .move(0, 1)
            .move(1, 0)
            .move(1, 1)
            .move(2, 0)
        val slot = slot<Score>()
        val repository : ScoreboardRepository = mockk(){
            coJustRun { addScore(capture(slot)) }
        }
        val useCase = AddScoreUseCase(repository)

        runBlocking { useCase(boardWithWinnerX) }

        slot.captured.apply {
            Assert.assertEquals(X, winner)
            Assert.assertEquals(5, numberOfMoves)
        }
    }
}