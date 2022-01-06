package day10

import readInput

fun main() {

    fun score(char: Char): Int {
        return when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> throw IllegalArgumentException("This char is not matched: $char")
        }
    }

    fun scorePart2(char: Char): Int {
        return when (char) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> throw IllegalArgumentException("This char is not matched: $char")
        }
    }

    fun notMatch(openingChar: Char, closingChar: Char): Boolean {
        return when (openingChar) {
            '(' -> closingChar != ')'
            '{' -> closingChar != '}'
            '[' -> closingChar != ']'
            '<' -> closingChar != '>'
            else -> throw IllegalArgumentException("Opening char is not an opening char $openingChar")
        }
    }

    fun getCorruptedChar(line: String): Char? {
        val indexFirstClosing = line.indexOfFirst { it.isClosingChar() }
        if (indexFirstClosing == -1) {
            return null
        }
        if (indexFirstClosing == 0) {
            return null
        }
        var openingChars = line.substring(0, indexFirstClosing)
        var rest = line.substring(indexFirstClosing, line.length)

        while (openingChars.isNotEmpty() && rest.isNotEmpty()) {
            val openingChar = openingChars.last()
            val closingChar = rest.first()
            if (notMatch(openingChar, closingChar)) {
                return closingChar
            }
            openingChars = openingChars.substring(0, openingChars.length - 1)
            rest = rest.substring(1, rest.length)
            val indexFirstClosing = rest.indexOfFirst { it.isClosingChar() }
            if (indexFirstClosing == -1) {
                return null
            }
            if (indexFirstClosing != 0) {
                openingChars += rest.substring(0, indexFirstClosing)
                rest = rest.substring(indexFirstClosing, rest.length)
            }
        }
        return null
    }

    fun part1(input: List<String>): Int {
        var totalScore = 0
        input.forEach {
            val corruptedChar = getCorruptedChar(it)
            if (corruptedChar != null) {
                totalScore += score(corruptedChar)
            }
        }
        return totalScore
    }

    fun determineMatchingClosings(incompleteLine: String): String {
        return incompleteLine.map {
            when(it) {
                '(' -> ')'
                '{' -> '}'
                '[' -> ']'
                '<' -> '>'
                else -> throw IllegalArgumentException("Opening char is not an opening char $it")
            }
        }.reversed().joinToString("")
    }

    fun determineAdditions(incompleteLine: String): String {
        val indexFirstClosing = incompleteLine.indexOfFirst { it.isClosingChar() }
        if (indexFirstClosing == -1) {
            return determineMatchingClosings(incompleteLine)
        }
        if (indexFirstClosing == 0) {
            return ""
        }
        var openingChars = incompleteLine.substring(0, indexFirstClosing)
        var rest = incompleteLine.substring(indexFirstClosing, incompleteLine.length)

        while (rest.isNotEmpty()) {
            openingChars = openingChars.substring(0, openingChars.length - 1)
            rest = rest.substring(1, rest.length)
            val indexFirstClosing = rest.indexOfFirst { it.isClosingChar() }
            if(indexFirstClosing == -1) {
                openingChars += rest.substring(0, rest.length)
                rest = ""
            }
            if (indexFirstClosing > 0) {
                openingChars += rest.substring(0, indexFirstClosing)
                rest = rest.substring(indexFirstClosing, rest.length)
            }
        }
        return determineMatchingClosings(openingChars)
    }

    fun determineIncompleteLineScore(incompleteLine: String): Long {
        val additions = determineAdditions(incompleteLine)
        var totalScore = 0L
        additions.forEach {
            totalScore *= 5L
            totalScore += scorePart2(it)
        }

        return totalScore
    }

    fun part2(input: List<String>): Long {
        var allScores = mutableListOf<Long>()
        var incompleteLines = mutableListOf<String>()
        input.forEach {
            val corruptedChar = getCorruptedChar(it)
            if (corruptedChar == null) {
                incompleteLines.add(it)
            }
        }

        incompleteLines.forEach {
            val score = determineIncompleteLineScore(it)
            allScores.add(score)
        }
        return allScores.sorted()[(allScores.size - 1) / 2]
    }

//    val input = readInput("day10/input_day10")
    val input = readInput("day10/input_day10_test")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

val openingChars = listOf('(', '{', '[', '<')
val closingChars = listOf(')', '}', ']', '>')

private fun Char.isOpeningChar(): Boolean {
    return openingChars.contains(this)
}

private fun Char.isClosingChar(): Boolean {
    return closingChars.contains(this)
}
