package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle

class Day09 : AoCPuzzle(9)
{
    override fun part1(input: List<String>): Any
    {
        return input.sumOf { extrapolate(it, true) }
    }

    override fun part2(input: List<String>): Any
    {
        return input.sumOf { extrapolate(it, false) }
    }

    private fun extrapolate(input: String, right: Boolean): Int
    {
        val numbers = input.split(" ").map { it.toInt() }

        val history = mutableListOf(numbers)
        var diff = numbers.zipWithNext().map { it.second - it.first }
        while (!diff.all { it == 0 })
        {
            history.add(diff)
            diff = history.last().zipWithNext().map { it.second - it.first }
        }
        history.add(diff)

        return if (right)
        {
            history.sumOf { it.last() }
        }
        else
        {
            history.map { it.first() }.reduceRight { i, acc -> i-acc }
        }
    }
}
