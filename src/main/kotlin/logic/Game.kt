package org.example.logic

/**
 * @param player1Board
 * @param player2Board
 * @param currentPlayer
 * @param playerBoards
 */
class Game(
    player1Board: Board = Board(),
    player2Board: Board = Board(),
    private var currentPlayer: Int = 0,
    private var playerBoards: MutableList<Board> = mutableListOf(
        player1Board, player2Board
    ),
) {
    private var run = false

    /**
     * @return current player (0 or 1)
     */
    fun getCurrentPlayer(): Int = currentPlayer

    /**
     * @return true if game is running and false otherwise
     */
    fun isGameRunning(): Boolean = run

    /**
     * @param user Int (0 or 1)
     * @return true if user win and false otherwise
     */
    fun isUserWin(user: Int): Boolean = playerBoards[user xor 1].isGameLost()

    /**
     * @param user Int (0 or 1)
     * @return true if user lose and false otherwise
     */
    fun isUserLose(user: Int): Boolean = playerBoards[user].isGameLost()

    /**
     * @param cell
     * @param player
     */
    // Это значит, что текущий игрок стреляет в ячейку cell, которая, конечно же, попадает по полю противника
    fun makeShot(cell: Cell, player: Int) {
        if (player != currentPlayer) {
            return
        }
        playerBoards[player xor 1].shot(cell)
        if (isUserLose(player)) {
            run = false
        }
        currentPlayer = currentPlayer xor 1
    }

    /**
     * @param user Int (0 or 1)
     * @param detail Boolean (show unmarked cells in ships)
     * @return string representation of the board
     */
    fun getBoard(user: Int, detail: Boolean = false): String = when {
        detail -> playerBoards[user].showStringDetailed()
        else -> playerBoards[user].showString()
    }
}
