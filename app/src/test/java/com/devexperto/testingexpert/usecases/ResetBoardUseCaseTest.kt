package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.BoardRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking

@RunWith(MockitoJUnitRunner::class)
class ResetBoardUseCaseTest {
    @Test
    fun `when invoke is called, then call repository reset`() {
        val repository: BoardRepository = mock()
        val useCAse = ResetBoardUseCase(repository)

        runBlocking { useCAse() }

        verifyBlocking(repository) { reset() }
    }
}