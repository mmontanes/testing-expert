package com.devexperto.testingexpert

import com.devexperto.testingexpert.domain.Draw
import com.devexperto.testingexpert.domain.Empty
import com.devexperto.testingexpert.domain.O
import com.devexperto.testingexpert.domain.TicTacToe
import com.devexperto.testingexpert.domain.X
import com.devexperto.testingexpert.domain.findWinner
import com.devexperto.testingexpert.domain.move
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TicTacToeTest {
    @Test
    fun `a new game has empty board`() {
        val game = TicTacToe()
        val flatted = game.board.flatten()
        assertTrue(game.board.flatten().all { it == Empty })
    }

    @Test
    fun `X plays first in a new game`() {
        val game = TicTacToe()
       assertEquals(game.currentPlayer, X)
    }

    @Test
    fun `players play alternate turns`() {
        val game = TicTacToe()
        val gameAfterFirstMove = game.move(0, 0)
        val gameAfterSecondMove = gameAfterFirstMove.move(0, 1)
        assertEquals(gameAfterFirstMove.currentPlayer, O)
        assertEquals(gameAfterSecondMove.currentPlayer, X)
    }

    @Test
    fun `an occupied cell cannot be played`() {
        val game = TicTacToe()
        val gameAfterFirstMove = game.move(0, 0)
        val gameAfterSecondMove = gameAfterFirstMove.move(0, 0)
        assertEquals(gameAfterFirstMove.board, gameAfterSecondMove.board)
//        assertEquals(gameAfterSecondMove.currentPlayer, O)
    }

    @Test
    fun `game ends when X fills a row`() {
        val game = TicTacToe()
            .move(2, 0)
            .move(1, 0)
            .move(2, 1)
            .move(1, 1)
            .move(2, 2)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), X)
    }

    @Test
    fun `game ends when X fills a column`(){
        val game = TicTacToe()
            .move(0, 1)
            .move(0, 0)
            .move(1, 1)
            .move(1, 0)
            .move(2, 1)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), X)
    }

    @Test
    fun `game ends when x fills the first diagonal`(){
        val game = TicTacToe()
            .move(0, 0)
            .move(0, 1)
            .move(1, 1)
            .move(1, 0)
            .move(2, 2)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), X)
    }

    @Test
    fun `game ends when x fills the second diagonal`(){
        val game = TicTacToe()
            .move(0, 2)
            .move(0, 1)
            .move(1, 1)
            .move(1, 0)
            .move(2, 0)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), X)
    }

    // TODO: Check the winner when O wins

    @Test
    fun `game ends when the board is filled without a winner`() {
        val game = TicTacToe()
            .move(0, 0) // X
            .move(0, 1) // O
            .move(0, 2) // X
            .move(1, 2) // O
            .move(1, 0) // X
            .move(2, 0) // O
            .move(1, 1)
            .move(2, 2)
            .move(2, 1)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), Draw)
    }

    @Test
    fun `game ends when 0 fills a row`() {
        val game = TicTacToe()
            .move(0, 0)
            .move(2, 0)
            .move(0, 1)
            .move(2, 1)
            .move(1, 0)
            .move(2, 2)
            .move(1, 1)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), O)
    }

    @Test
    fun `game ends when O fills a column`() {
        val game = TicTacToe()
            .move(0, 0)
            .move(0, 1)
            .move(1, 0)
            .move(1, 1)
            .move(2, 2)
            .move(2, 1)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), O)
    }

    @Test
    fun `game ends when O fills the first diagonal`() {
        val game = TicTacToe()
            .move(0, 2)
            .move(0, 0)
            .move(1, 0)
            .move(1, 1)
            .move(2, 0)
            .move(2, 2)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), O)
    }

    @Test
    fun `game ends when O fills the second diagonal`() {
        val game = TicTacToe()
            .move(0, 0)
            .move(0, 2)
            .move(1, 0)
            .move(1, 1)
            .move(2, 2)
            .move(2, 0)
        println(game.board[0])
        println(game.board[1])
        println(game.board[2])
        assertEquals(game.findWinner(), O)
    }

    @Test
    fun `game returns null when asked winner in the middle of the game`() {
        val game = TicTacToe()
            .move(0, 0) // X
            .move(0, 1) // O
        assertEquals(game.findWinner(), null)
    }
}