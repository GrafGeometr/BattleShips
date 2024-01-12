package org.example.logic

class Game(board1: Board = Board(), board2: Board = Board(), whoUserMove: Int = 0) {
    private var boards: MutableList<Board> = mutableListOf(board1, board2)
    private var whoMove = whoUserMove // 0 or 1
    private var run = false

    fun getWhoMove(): Int {
        return whoMove
    }

    fun gameRunning(): Boolean {
        return run
    }

    fun isWin(user: Int): Boolean {
        return boards[user xor 1].gameIsLost()
    }

    fun isLose(user: Int): Boolean {
        return boards[user].gameIsLost()
    }

    // Это значит, что текущий игрок стреляет в ячейку cell, которая, конечно же, попадает по полю противника
    fun shotUser(cell: Cell, user: Int) {
        if (user != whoMove)
            return
        boards[user xor 1].shot(cell)
        if (isLose(user))
            run = false
    }

    fun getBoard(user: Int, detail: Boolean = false): String {
        if (detail)
            return boards[user].showStringDetailed()
        else
            return boards[user].showString()
    }

    fun startGame() {
        run = true
    }
}