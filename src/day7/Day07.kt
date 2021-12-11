package day7

import readInput
import kotlin.math.absoluteValue

fun main() {

    fun part1(startCrabs: Map<Int, Int>): Long {
        var lines = mutableMapOf<Int, Long>()
        for(line in startCrabs.keys.minOf { it } .. startCrabs.keys.maxOf {it}) {
            val fuel = startCrabs.map {
//                println("Line $line and key ${it.key} difference ${(it.key - line).absoluteValue} times ${it.value}")
                ((it.key - line).absoluteValue.toLong() * it.value.toLong())
            }.sum()
            lines[line] = fuel
        }

        val leastFuel = lines.minByOrNull { it.value }
        println("Least fuel $leastFuel")
        return leastFuel?.value ?: 0
    }

    fun part2(startCrabs: Map<Int, Int>): Long {
        var lines = mutableMapOf<Int, Long>()
        for(line in startCrabs.keys.minOf { it } .. startCrabs.keys.maxOf {it}) {
            val fuel = startCrabs.map {
                val steps = (it.key - line).absoluteValue
                val singleFuel = (1 .. steps).sum()
                singleFuel * it.value.toLong()
            }.sum()
            lines[line] = fuel
        }

        val leastFuel = lines.minByOrNull { it.value }
        println("Least fuel $leastFuel")
        return leastFuel?.value ?: 0
    }

    val input = readInput("day7/input_day7")
//    val input = readInput("day7/input_day7_test")
    val startCrabs = input[0].split(",").map { it.toInt() }.groupingBy { it }.eachCount()
    println("part1: ${part1(startCrabs)}")
    println("part2: ${part2(startCrabs)}")
}
