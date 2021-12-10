package day5

import readInput
import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val grid : MutableList<Point> = mutableListOf()
        input.forEach { line ->
            val coords = line.split(" -> ")
            val startCoords = getCoords(coords[0])
            val endCoords = getCoords(coords[1])

            //vertical line
            if (startCoords.first == endCoords.first) {
//                println("vertical")
                for (y in startCoords.second toward endCoords.second) {
                    grid.find { it.y == y && it.x == startCoords.first }?.cross() ?: grid.add(Point(startCoords.first, y, 1))
                }
            }
            //horizontal line
            if (startCoords.second == endCoords.second) {
//                println("horizontal")
                for (x in startCoords.first toward endCoords.first) {
                    grid.find { it.y == startCoords.second && it.x == x }?.cross() ?: grid.add(Point(x, startCoords.second, 1))
                }
            }
        }

        return grid.count { it.crossed > 1 }
    }

    fun part2(input: List<String>): Int {
        val grid : MutableList<Point> = mutableListOf()
        input.forEach { line ->
            val coords = line.split(" -> ")
            val startCoords = getCoords(coords[0])
            val endCoords = getCoords(coords[1])

            //vertical line
            if (startCoords.first == endCoords.first) {
                for (y in startCoords.second toward endCoords.second) {
                    grid.find { it.y == y && it.x == startCoords.first }?.cross() ?: grid.add(Point(startCoords.first, y, 1))
                }
            } else  if (startCoords.second == endCoords.second) {
                //horizontal line
                for (x in startCoords.first toward endCoords.first) {
                    grid.find { it.y == startCoords.second && it.x == x }?.cross() ?: grid.add(Point(x, startCoords.second, 1))
                }
            } else if((startCoords.first - endCoords.first).absoluteValue == (startCoords.second - endCoords.second).absoluteValue) {
                //diagonal line
                println("diagonal line $startCoords to $endCoords")
                var y = startCoords.second
                for (x in startCoords.first toward endCoords.first) {
                        grid.find { it.y == y && it.x == x }?.cross() ?: grid.add(Point(x, y, 1))
                        if(startCoords.second > endCoords.second) y-- else y++
                }
            }
        }

        return grid.count { it.crossed > 1 }
    }

    val input = readInput("day5/input_day5")
//    val input = readInput("day5/input_day5_test")
    println(part1(input))
    println(part2(input))
}

private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

fun getCoords(coords: String): Pair<Int, Int> {
    val splitted = coords.split(",")
    return Pair(splitted[0].trim().toInt(), splitted[1].trim().toInt())

}

data class Point(val x: Int, val y: Int, var crossed: Int = 0) {
    fun cross() {
        crossed++
    }
}
