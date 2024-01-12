package org.example.logic

class Game {
    private var boards: MutableList<Board> = mutableListOf(Board(), Board())
    private var who_move = 0 // 0 or 1
    private var run = false

    constructor(board1: Board, board2: Board, who_move: Int = 0) {
        boards[0] = board1
        boards[1] = board2
        this.who_move = who_move
    }

    fun getWhoMove(): Int {
        return who_move
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
        if (user != who_move)
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