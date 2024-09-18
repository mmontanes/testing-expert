package com.devexperto.testingexpert.usecases

import com.devexperto.testingexpert.data.BoardRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class MakeBoardMoveUseCaseTest{

    @get:Rule
    val mockKRule = MockKRule(this)

    @Test
    fun `when invoke is called, then call repository move`() {
        val repository : BoardRepository = mockk() {
            coJustRun { move(any(), any()) }
        }
        val useCase = MakeBoardMoveUseCase(repository)

        runBlocking { useCase(0, 0) }

        coVerify { repository.move(0, 0) }
    }
}