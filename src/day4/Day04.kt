package day4

import readInput

fun main() {

    fun part1(drawnNumbers: List<Int>, boards: List<Board>): Int {
        drawnNumbers.forEach { drawnNumber ->
            boards.forEach { handleSingleBoard(it, drawnNumber) }
            val winnerBoard: Board? = findWinningBoard(boards)
            if (winnerBoard != null) {
                val sumUndrawnNumbers = winnerBoard.points.filter { !it.drawn }.sumOf { it.value }
                return sumUndrawnNumbers * drawnNumber
            }
        }

        return 0
    }

    fun part2(drawnNumbers: List<Int>, boards: List<Board>): Int {
        drawnNumbers.forEach { drawnNumber ->
            boards.forEach { handleSingleBoard(it, drawnNumber) }
            val winningBoards = markWinningBoards(boards)
            if (boards.all { it.won }) {
                val sumUndrawnNumbers = winningBoards[0].points.filter { !it.drawn }.sumOf { it.value }
                return sumUndrawnNumbers * drawnNumber
            }
        }

        return 0
    }

    val drawnNumbers = readInput("day4/input_day4_numbers")[0].split(",").map { it.toInt() }.toList()
    val boards = createBoards(readInput("day4/input_day4_boards"))
    println(part1(drawnNumbers, boards))
    println(part2(drawnNumbers, boards))
}

fun handleSingleBoard(board: Board, drawnNumber: Int) {
    board.points.filter { it.value == drawnNumber }.forEach {
        it.drawn = true
    }
}

fun findWinningBoard(boards: List<Board>): Board? {
    boards.filter { !it.won }.map { board ->
        val points = board.points
        val maxY = points.maxOf { it.y }
        for (y in 0..maxY) {
            if (points.filter { it.y == y }.all { it.drawn })
                return board
        }

        val maxX = points.maxOf { it.x }
        for (x in 0..maxX) {
            if (points.filter { it.x == x }.all { it.drawn })
                return board
        }
    }
    return null
}

fun markWinningBoards(boards: List<Board>): List<Board> {
    val winningBoards = mutableListOf<Board>()
    boards.filter { !it.won }.forEach { board ->
        val points = board.points
        val maxY = points.maxOf { it.y }
        for (y in 0..maxY) {
            if (points.filter { it.y == y }.all { it.drawn }) {
                board.won = true
                winningBoards.add(board)
                continue
            }
        }

        val maxX = points.maxOf { it.x }
        for (x in 0..maxX) {
            if (points.filter { it.x == x }.all { it.drawn }) {
                board.won = true
                winningBoards.add(board)
                continue
            }
        }
    }
    return winningBoards
}

fun createBoards(input: List<String>): List<Board> {

    val boards = mutableListOf<Board?>()
    var board: Board? = null
    var row = 0
    input.forEach {
        val line = it.trim()
        if (line.isEmpty()) {
            boards.add(board)
            board = null
            row = 0
        } else {
            val currentPoints = board?.points ?: emptyList()
            val newPoints = currentPoints + createPoints(line, row)
            board = Board(newPoints)
            row++
        }
    }

    return boards
        .filterNotNull()
        .toList()
}

fun createPoints(boardRow: String, rowNr: Int): List<Point> {
    var columnNr = 0
    return boardRow.split(" ").filter { it.trim().isNotEmpty() }.map { it.trim().toInt() }
        .map {
            val point = Point(rowNr, columnNr, it)
            columnNr++
            point
        }.toList()
}

data class Board(val points: List<Point>, var won: Boolean = false)

data class Point(val x: Int, val y: Int, val value: Int, var drawn: Boolean = false)