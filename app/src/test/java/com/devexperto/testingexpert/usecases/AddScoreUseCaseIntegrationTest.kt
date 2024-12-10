package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.ScoreboardRepository
import com.devexperto.testingexpert.data.datasource.ScoreLocalDataSourceFake
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


class AddScoreUseCaseIntegrationTest{

    @Test
    fun `when invoke is called, then call repository addScore`() {
//        val boardWithWinnerX = TicTacToe()
//            .move(0, 0)
//            .move(0, 1)
//            .move(1, 0)
//            .move(1, 1)
//            .move(2, 0)
//        val scoreLocalDataSource = ScoreLocalDataSourceFake(boardWithWinnerX)
//        val scoreboardRepository = ScoreboardRepository(scoreLocalDataSource)
//        val useCase = AddScoreUseCase(scoreboardRepository)
//
//        runBlocking { useCase(boardWithWinnerX) }
//
//        slot.captured.apply {
//            Assert.assertEquals(X, winner)
//            Assert.assertEquals(5, numberOfMoves)
//        }
    }
}