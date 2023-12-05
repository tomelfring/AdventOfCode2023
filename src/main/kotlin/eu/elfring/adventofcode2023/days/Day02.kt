package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle

class Day02 : AoCPuzzle(2)
{
    override fun part1(input: List<String>): Any
    {
        val maxRedCubes = 12
        val maxGreenCubes = 13
        val maxBlueCubes = 14

        val mapped = input.map(::parseLine)

        return mapped.sumOf { if (it.second.all { it.isValid(maxRedCubes, maxGreenCubes, maxBlueCubes) }) it.first else 0 }
    }

    override fun part2(input: List<String>): Any
    {
        val mapped = input.map(::parseLine)

        return mapped.sumOf {
            val minReds = it.second.maxOf { it.red }
            val minGreens = it.second.maxOf { it.green }
            val minBlues = it.second.maxOf { it.blue }

            minReds*minGreens*minBlues
        }
    }

    private fun parseLine(line: String): Pair<Int, List<Revealed>>
    {
        val (game, drawn) = line.split(":")

        val revealed = drawn.split(";").map {
            val reds = """(\d+) red""".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            val greens = """(\d+) green""".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            val blues = """(\d+) blue""".toRegex().find(it)?.groupValues?.get(1)?.toInt() ?: 0
            Revealed(reds, greens, blues)
        }

        return game.filter { it.isDigit() }.toInt() to revealed
    }

    private data class Revealed(val red: Int, val green: Int, val blue: Int)
    {
        fun isValid(maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean
        {
            return red<=maxRed && green<=maxGreen && blue<=maxBlue
        }
    }
}
