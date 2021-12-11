package day6

import readInput

fun main() {

    fun part1(input: List<String>, days: Int): Long {
        val startFishes = input[0].split(",").map { it.toInt() }.groupingBy { it }.eachCount()

        var currentGen = startFishes.toSortedMap().map { it.key to it.value.toLong() }.toMap()
        for(day in 1 .. days) {
            val nextGen = mutableMapOf<Int, Long>()
            currentGen.forEach {
                when(it.key) {
                    0 -> {
                        nextGen[6] = currentGen[7]?.plus(it.value) ?: it.value
                        nextGen[8] = it.value
                    }
                    7 -> nextGen[6] =  currentGen[0]?.plus(it.value) ?: it.value
                    8 -> nextGen[7] = it.value
                    in 1 .. 6 -> nextGen[it.key - 1] = it.value
                    else -> throw IllegalStateException("Fish with ${it.key} days left shouldn't exist")
                }
            }
            currentGen = nextGen.toSortedMap()
//            println("Day $day has $currentGen")
        }
        return currentGen.values.sum()
    }

    val input = readInput("day6/input_day6")
//    val input = readInput("day6/input_day6_test")
    println(part1(input, 80))
    println("part2: ${part1(input, 256)}")
}
