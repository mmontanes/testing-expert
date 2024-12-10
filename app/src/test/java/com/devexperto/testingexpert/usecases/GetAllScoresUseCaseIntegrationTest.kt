package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.ScoreboardRepository
import com.devexperto.testingexpert.data.datasource.ScoreLocalDataSource
import com.devexperto.testingexpert.data.datasource.ScoreLocalDataSourceFake
import com.devexperto.testingexpert.domain.O
import com.devexperto.testingexpert.domain.Score
import com.devexperto.testingexpert.domain.X
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.Date


class GetAllScoresUseCaseIntegrationTest {

    @Test
    fun `when invoke is called, then return scores from repository`() {
        val expectedScores = listOf(
            Score(X, 5, Date()),
            Score(O, 3, Date()),
            Score(X, 7, Date()),
            Score(X, 9, Date()),
        )
        val scoreLocalDataSource : ScoreLocalDataSource = ScoreLocalDataSourceFake(expectedScores)
        val repository = ScoreboardRepository(scoreLocalDataSource)

        val useCase = GetAllScoresUseCase(repository)
        val scores = runBlocking {  useCase().first() }

        Assert.assertEquals(expectedScores, scores)
    }
}