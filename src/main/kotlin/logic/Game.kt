package org.example.logic

class Game(board1: Board = Board(), board2: Board = Board(), private var whoMoves: Int) {
    private var boards: MutableList<Board> = mutableListOf(board1, board2)
    private var run = false

    fun getWhoMove(): Int = whoMoves

    fun gameRunning(): Boolean = run

    fun isWin(user: Int): Boolean = boards[user xor 1].gameIsLost()

    fun isLose(user: Int): Boolean = boards[user].gameIsLost()

    // Это значит, что текущий игрок стреляет в ячейку cell, которая, конечно же, попадает по полю противника
    fun makeShot(cell: Cell, user: Int) {
        if (user != whoMoves)
            return
        boards[user xor 1].shot(cell)
        if (isLose(user))
            run = false
        whoMoves = whoMoves xor 1
    }

    fun getBoard(user: Int, detail: Boolean = false): String = when {
        detail -> boards[user].showStringDetailed()
        else -> boards[user].showString()
    }

    fun startGame(): Boolean = run
}