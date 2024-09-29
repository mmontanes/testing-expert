package com.devexperto.testingexpert.data

import com.devexperto.testingexpert.data.datasource.ScoreLocalDataSourceFake
import com.devexperto.testingexpert.domain.Score
import com.devexperto.testingexpert.domain.X
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class ScoreboardRepositoryIntegrationTest {

    private val expectedScores = Score(X, 3, Date())

    @Test
    fun `when a score is added, it is added to the local data source`() {
        val localDataSource = ScoreLocalDataSourceFake()
        val repository = ScoreboardRepository(localDataSource)

        val scores = runBlocking {
            repository.addScore(expectedScores)
            repository.scores.first()
        }

        assertEquals(expectedScores, scores.first())
    }

    @Test
    fun `when scores are requested, they are retrieved from the local data source`() {
        val localDataSource = ScoreLocalDataSourceFake(listOf(expectedScores))
        val repository = ScoreboardRepository(localDataSource)

        val score = runBlocking { repository.scores.first() }

        assertEquals(expectedScores, score.first())
    }
}