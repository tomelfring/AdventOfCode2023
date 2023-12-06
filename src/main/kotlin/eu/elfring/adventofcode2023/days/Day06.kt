package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle

class Day06 : AoCPuzzle(6)
{
    override fun part1(input: List<String>): Any
    {
        val digitRegex = """(\d+)""".toRegex()
        val times = digitRegex.findAll(input[0]).map { it.value.toLong() }.toList()
        val distances = digitRegex.findAll(input[1]).map { it.value.toLong() }.toList()

        val pairs = times.zip(distances)

        return pairs.map(::calculateTimesWithEnoughDistance).reduce { acc, value -> acc*value }
    }

    override fun part2(input: List<String>): Any
    {
        val time = input[0].filter { it.isDigit() }.toLong()
        val distance = input[1].filter { it.isDigit() }.toLong()

        return calculateTimesWithEnoughDistance(time to distance)
    }

    private fun calculateTimesWithEnoughDistance(pair: Pair<Long, Long>): Int
    {
        val (time, distanceNeeded) = pair

        return (0L..time).count { timePressed ->
            val timeToRace = time-timePressed
            val speed = timePressed

            speed*timeToRace>distanceNeeded
        }
    }
}
