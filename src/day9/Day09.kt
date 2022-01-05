package day9

import readInput

fun main() {

    fun createPoints(input: List<String>): List<Point> {
        val points = mutableListOf<Point>()
        input.forEachIndexed { y, line ->
            line.toList().forEachIndexed { x, char ->
                points.add(Point(x, y, char.digitToInt()))
            }
        }
        return points.toList()
    }

    fun isLowest(current: Point, points: List<Point>): Boolean {
        val xMin1 = points.firstOrNull { it.x == (current.x - 1) && it.y == current.y }
        if (xMin1 != null && xMin1.value <= current.value) return false

        val xPlus1 = points.firstOrNull { it.x == (current.x + 1) && it.y == current.y }
        if (xPlus1 != null && xPlus1.value <= current.value) return false

        val yMin1 = points.firstOrNull { it.x == current.x && it.y == (current.y - 1) }
        if (yMin1 != null && yMin1.value <= current.value) return false

        val yPlus1 = points.firstOrNull { it.x == current.x && it.y == (current.y + 1) }
        if (yPlus1 != null && yPlus1.value <= current.value) return false

        return true
    }

    fun part1(input: List<String>): Int {
        val points = createPoints(input)
        return points.filter { isLowest(it, points) }.sumOf { it.value + 1 }
    }

    fun part2(input: List<String>): Int {
        val points = createPoints(input)
        var newBasin = 1
        //basinNumber, size
        val allBasins = mutableMapOf<Int, Int>()
        val helper = Helper()
        points.forEach {
            if (it.value != 9 && it.basin == 0) {
                it.basin = newBasin
                val size = helper.findCompleteBasin(newBasin, it, points)
                allBasins[newBasin] = size
                newBasin++
            }
        }

        //find 3 largest basins
        val sortedBasinSizes = allBasins.values.sortedDescending()
        return sortedBasinSizes[0] * sortedBasinSizes[1] * sortedBasinSizes[2]
    }

    val input = readInput("day9/input_day9")
//    val input = readInput("day9/input_day9_test")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

class Helper {

    fun findCompleteBasin(newBasin: Int, startingPoint: Point, points: List<Point>): Int {

        var size = 1
        //up
        size += handlePoint(points, startingPoint, 0, -1, newBasin)
        //down
        size += handlePoint(points, startingPoint, 0, 1, newBasin)
        //left
        size += handlePoint(points, startingPoint, -1, 0, newBasin)
        //right
        size += handlePoint(points, startingPoint, 1, 0, newBasin)
        return size
    }

    private fun handlePoint(points: List<Point>, startingPoint: Point, plusX: Int, plusY: Int, newBasin: Int): Int {
        val point = points.firstOrNull { it.x == (startingPoint.x + plusX) &&
                it.y == (startingPoint.y + plusY) && it.value != 9 && it.basin == 0}

        if(point != null) {
            point.basin = newBasin
            return findCompleteBasin(newBasin, point, points)
        }

        return 0
    }

}

data class Point(val x: Int, val y: Int, val value: Int, var basin: Int = 0)