package com.devexperto.testingexpert.data



import com.devexperto.testingexpert.data.datasource.ScoreLocalDataSource
import com.devexperto.testingexpert.domain.Score
import com.devexperto.testingexpert.domain.X
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.Date


class ScoreboardRepositoryTest  {

    @get:Rule
    val mockkRule = MockKRule(this)

    @Test
    fun `when a score is added, it is added to the local data source`() {
        val expectedScores = listOf(Score(X, 3, Date()))
        val localDataSource: ScoreLocalDataSource = mockk()  {
            coEvery { scores } returns flowOf(expectedScores)
            coJustRun { addScore(any()) }
        }
        val repository = ScoreboardRepository(localDataSource)
        val score = Score(X, 3, Date())

        runBlocking {
            repository.addScore(score)
        }

        coVerify { localDataSource.addScore(score) }
    }

    @Test
    fun `when scores are requested, they are retrieved from the local data source`() {
        val expectedScores = listOf(Score(X, 3, Date()))
        val localDataSource: ScoreLocalDataSource = mockk() {
            coEvery { scores } returns flowOf(expectedScores)
        }
        val repository = ScoreboardRepository(localDataSource)

        val score = runBlocking { repository.scores.first() }

        Assert.assertEquals(expectedScores, score)
    }
}