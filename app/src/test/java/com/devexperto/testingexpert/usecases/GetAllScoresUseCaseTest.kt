package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.ScoreboardRepository
import com.devexperto.testingexpert.domain.Score
import com.devexperto.testingexpert.domain.X
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GetAllScoresUseCaseTest {
    @Test
    fun `when invoke is called, then return scores from repository`() {
        val expectedScores = listOf(Score(X, 3, Date()))
        val repository: ScoreboardRepository = mock() {
            onBlocking { scores } doReturn flowOf(expectedScores)
        }
        val useCase = GetAllScoresUseCase(repository)

        val scores = runBlocking {  useCase().first() }

        Assert.assertEquals(expectedScores, scores)
    }
}