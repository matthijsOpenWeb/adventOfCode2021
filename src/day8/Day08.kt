package day8

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val outputs = input.map { it.split(" | ")[1] }.flatMap { it.split(" ") }
        val part1DigitLengths = listOf(2, 3, 4, 7) //the digit lengths for 1, 4, 7, 8
        return outputs.count { part1DigitLengths.contains(it.length) }
    }

    fun createNumber(output: List<String>, numbers: List<Number>): Int {
        var digit = ""
        output.forEach { outputDigit ->
                val number = numbers.find { outputDigit.toList().sorted() == it.letters.sorted() }
                digit += number?.number
        }
        return digit.toInt()
    }

    fun findUnusedValue(positions: Map<Int, Char>): Char {
        return ('a'..'g').minus(positions.values)[0]

    }

    fun part2(inputLines: List<String>): Long {

        var total = 0L
        inputLines.forEach { line ->
            val inputs = line.split(" | ")[0].split(" ")
            val outputs = line.split(" | ")[1].split(" ")

            val allIn = inputs + outputs
            val numbers = initNumbers()
            val positions: MutableMap<Int, Char> = initPositions()

            numbers[1]?.letters = allIn.find { it.length == 2 }?.toList()?.sorted() ?: emptyList()
            numbers[4]?.letters = allIn.find { it.length == 4 }?.toList()?.sorted() ?: emptyList()
            numbers[7]?.letters = allIn.find { it.length == 3 }?.toList()?.sorted() ?: emptyList()
            numbers[8]?.letters = allIn.find { it.length == 7 }?.toList()?.sorted() ?: emptyList()

            positions[2] = (numbers[7]!!.letters.minus(numbers[1]!!.letters))[0]
            val allDigitsAsLetters = allIn.map { it.toList().sorted() }.toSet()
            println("All digits in letters: $allDigitsAsLetters")
            val occurrences = mutableMapOf<Char, Int>()
            for (letter in 'a'..'g') {
                occurrences[letter] = allDigitsAsLetters.count { it.contains(letter) }
            }
            println("Occurrences $occurrences")
            positions[1] = occurrences.filter { it.value == 6 }.keys.firstOrNull() ?: '.'
            positions[4] = occurrences.filter { it.value == 9 }.keys.firstOrNull() ?: '.'
            positions[6] = occurrences.filter { it.value == 4 }.keys.firstOrNull() ?: '.'

            //position 7 is number 4 - number 1 - position 1
            positions[7] = numbers[4]!!.letters.minus(numbers[1]!!.letters).minus(positions[1]).getOrNull(0) ?: '.'
            positions[5] = occurrences.filter { it.value == 7 }.keys.minus(positions[7]).firstOrNull()
                ?: '.'
            positions[3] = occurrences.filter { it.value == 8 }.keys.minus(positions[2]).firstOrNull()
                ?: '.'
                positions.filter { it.value == '.' }.forEach {
                positions[it.key] = findUnusedValue(positions)
            }
            println("All positions: $positions")

            numbers[0]?.letters = numbers[0]?.positions?.mapNotNull { positions[it] }?.toList() ?: emptyList()
            numbers[2]?.letters = numbers[2]?.positions?.mapNotNull { positions[it] }?.toList() ?: emptyList()
            numbers[3]?.letters = numbers[3]?.positions?.mapNotNull { positions[it] }?.toList() ?: emptyList()
            numbers[5]?.letters = numbers[5]?.positions?.mapNotNull { positions[it] }?.toList() ?: emptyList()
            numbers[6]?.letters = numbers[6]?.positions?.mapNotNull { positions[it] }?.toList() ?: emptyList()
            numbers[9]?.letters = numbers[9]?.positions?.mapNotNull { positions[it] }?.toList() ?: emptyList()

            //all numbers have the correct set of letters
            total += createNumber(outputs, numbers.values.toList())
        }
        return total
    }

    val input = readInput("day8/input_day8")
//    val input = readInput("day8/input_day8_test")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}


fun initPositions() = mutableMapOf(
    1 to '.',
    2 to '.',
    3 to '.',
    4 to '.',
    5 to '.',
    6 to '.',
    7 to '.',
)
// 1 occurs 6
// 2 occurs 8
// 3 occurs 8
// 4 occurs 9
// 5 occurs 7
// 6 occurs 4
// 7 occurs 7

fun initNumbers() = mapOf<Int, Number>(
    0 to Number(0, 6, listOf(1, 2, 3, 4, 5, 6)),
    1 to Number(1, 2, listOf(3, 4)),
    2 to Number(2, 5, listOf(2, 3, 5, 6, 7)),
    3 to Number(3, 5, listOf(2, 3, 4, 5, 7)),
    4 to Number(4, 4, listOf(1, 3, 4, 7)),
    5 to Number(5, 5, listOf(1, 2, 4, 5, 7)),
    6 to Number(6, 6, listOf(1, 2, 4, 5, 6, 7)),
    7 to Number(7, 3, listOf(2, 3, 4)),
    8 to Number(8, 7, listOf(1, 2, 3, 4, 5, 6, 7)),
    9 to Number(9, 6, listOf(1, 2, 3, 4, 5, 7)),
)

data class Number(val number: Int, val length: Int, val positions: List<Int>, var letters: List<Char> = emptyList())