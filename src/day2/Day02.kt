package day2

import readInput

fun main() {
    val forward = "forward"
    val down = "down"
    val up = "up"

    fun countMovementPart1(input: List<String>, movement: String) = input
        .filter { it.startsWith(movement) }
        .sumOf { it.removePrefix(movement).trim().toInt() }

    fun part1(input: List<String>): Int {
        val horizontal = countMovementPart1(input, forward)

        val down = countMovementPart1(input, down)
        val up = countMovementPart1(input, up)
        val depth = down - up

        return depth * horizontal
    }

    fun part2(input: List<String>): Int {

        var position = Position(0, 0, 0)

        input.map {
            val split = it.split(" ")
            Pair(split[0], split[1].toInt())
        }.forEach {
            position = when (it.first) {
                forward -> {
                    val newHorizontal = position.horizontal + it.second
                    val newDepth = position.aim * it.second + position.depth
                    Position(position.aim, newDepth, newHorizontal)
                }
                up -> {
                    val newAim = position.aim - it.second
                    Position(newAim, position.depth, position.horizontal)
                }
                down -> {
                    val newAim = position.aim + it.second
                    Position(newAim, position.depth, position.horizontal)
                }
                else -> throw IllegalStateException("This movement isnt mapped: ${it.first}")
            }
        }

        return position.depth * position.horizontal
    }

    val input = readInput("day2/input_day2")
    println(part1(input))
    println(part2(input))
}

data class Position(val aim: Int, val depth: Int, val horizontal: Int)