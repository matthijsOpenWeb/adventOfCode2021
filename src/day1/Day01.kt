fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .zipWithNext()
            .count { it.first < it.second }
    }

    fun part2(input: List<String>): Int {
       return input
            .asSequence()
            .map { it.toInt() }
            .zipWithNext()
            .zipWithNext()
            .map { it.first.first + it.first.second + it.second.second }
            .zipWithNext()
            .count { it.first < it.second }
    }

    val input = readInput("day1/input_day1")
    println(part1(input))
    println(part2(input))
}
