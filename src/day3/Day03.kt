package day3

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var gammaBit = ""
        for (i in 0 until input[0].length) {
            gammaBit += input.map { it[i] }
                .groupingBy { it }
                .eachCount()
                .maxByOrNull { it.value }?.key
        }
        println("gamma in bits = $gammaBit")

        val epsilonBit = gammaBit.toList().map {
            when (it) {
                '0' -> '1'
                '1' -> '0'
                else -> throw IllegalStateException("Character $it is not mapped and is not expected 0 or 1")
            }
        }.toList().joinToString(separator = "")
        println("epsilon in bits = $epsilonBit")

        val epsilon = Integer.parseInt(epsilonBit, 2)
        val gamma = Integer.parseInt(gammaBit, 2)
        println("Decimals: e = $epsilon & g = $gamma")

        return epsilon * gamma
    }

    fun groupCount(reduceInput: List<String>, i: Int): Pair<Int, Int> {
        val eachCount = reduceInput.map { it[i] }
            .groupingBy { it }
            .eachCount()
        val zeroCount = eachCount['0'] ?: 0
        val oneCount = eachCount['1'] ?: 0
        return Pair(zeroCount, oneCount)
    }

    fun findMaxBit(reduceInput: List<String>, i: Int, default: Char): Char {
        val (zeroCount, oneCount) = groupCount(reduceInput, i)
        return if (zeroCount > oneCount) '0' else if (oneCount > zeroCount) '1' else default
    }

    fun findMinBit(reduceInput: List<String>, i: Int, default: Char): Char {
        val (zeroCount, oneCount) = groupCount(reduceInput, i)
        return if (zeroCount > oneCount) '1' else if (oneCount > zeroCount) '0' else default
    }

    fun findData(input: List<String>, default : Char, min: Boolean = false): String {
        var reduceInput = input
        for (i in 0 until input[0].length) {
            val bit = if(min) findMinBit(reduceInput, i, default) else findMaxBit(reduceInput, i, default)
            reduceInput = reduceInput.filter { it[i] == bit }.toList()
            if(reduceInput.size == 1) {
                return reduceInput[0]
            }
        }
        return ""
    }

    fun part2(input: List<String>): Int {
        var oxygenBit = findData(input, '1')
        println("oxygen in bits = $oxygenBit")
        var co2ScrubberBit = findData(input, '0', true)
        println("co2 scrubber in bits = $co2ScrubberBit")

        val co2Scrubber = Integer.parseInt(co2ScrubberBit, 2)
        val oxygen = Integer.parseInt(oxygenBit, 2)
        println("Decimals: co2 = $co2Scrubber & o = $oxygen")

        return co2Scrubber * oxygen
    }

    val input = readInput("day3/input_day3")
    println(part1(input))
    println(part2(input))
}
