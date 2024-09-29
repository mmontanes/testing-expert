package com.devexperto.testingexpert.data.datasource

import com.devexperto.testingexpert.domain.Score
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ScoreLocalDataSourceFake(scoreList: List<Score> = emptyList())
    : ScoreLocalDataSource  {
    private val inMemoryScores = MutableStateFlow(scoreList)
    override val scores = inMemoryScores

    override suspend fun addScore(score: Score) {
        inMemoryScores.update { it + score }
    }
}