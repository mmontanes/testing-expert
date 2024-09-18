package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.ScoreboardRepository
import com.devexperto.testingexpert.domain.Score
import com.devexperto.testingexpert.domain.X
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.Date


class GetAllScoresUseCaseTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @Test
    fun `when invoke is called, then return scores from repository`() {
        val expectedScores = listOf(Score(X, 3, Date()))
        val repository: ScoreboardRepository = mockk {
            coEvery { scores } returns flowOf(expectedScores)
        }
        val useCase = GetAllScoresUseCase(repository)

        val scores = runBlocking {  useCase().first() }

        Assert.assertEquals(expectedScores, scores)
    }
}