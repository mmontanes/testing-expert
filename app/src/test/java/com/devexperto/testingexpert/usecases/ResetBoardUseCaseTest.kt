package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.BoardRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test


class ResetBoardUseCaseTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @Test
    fun `when invoke is called, then call repository reset`() {
        val repository: BoardRepository = mockk{
            coJustRun { reset() }
        }
        val useCAse = ResetBoardUseCase(repository)

        runBlocking { useCAse() }

        coVerify { repository.reset() }
    }
}