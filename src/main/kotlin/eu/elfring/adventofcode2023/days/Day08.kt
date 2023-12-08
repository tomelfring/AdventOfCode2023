package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle
import java.math.BigInteger

class Day08 : AoCPuzzle(8)
{
    override fun part1(input: List<String>): Any
    {
        val instructions = input.first()
        val network = input.drop(2).associate { toNodes(it) }

        // solve
        var currentPos = "AAA"
        var steps = 0
        while (currentPos != "ZZZ")
        {
            val instruction = instructions[steps%instructions.length]
            currentPos = if (instruction == 'L') network[currentPos]!!.first else network[currentPos]!!.second

            steps++
        }

        return steps
    }

    override fun part2(input: List<String>): Any
    {
        val instructions = input.first()
        val network = input.drop(2).associate { toNodes(it) }

        // solve
        var currentPositions = listOf(network.filterKeys { it.endsWith("A") }).flatMap { it.keys }

        var steps = 0

        // Instruction index, when Z was found
        val loopDetection = mutableMapOf<Int, Int>()
        // Instruction index, first Z + stepsize
        val loops = mutableMapOf<Int, Int>()

        while (loops.size != currentPositions.size)
        {
            val instruction = instructions[steps%instructions.length]

            currentPositions = currentPositions.map {
                if (instruction == 'L') network[it]!!.first else network[it]!!.second
            }

            currentPositions.forEachIndexed { index, s ->
                if (s.endsWith("Z"))
                {
                    // First time reaching Z
                    if (loopDetection[index] == null)
                    {
                        loopDetection[index] = steps
                    }
                    // Second time reaching Z, calculate offset & roundtrip steps
                    else if (loops[index] == null)
                    {
                        loops[index] = steps-loopDetection[index]!!
                    }
                }
            }

            steps++
        }

        println(loops)

        val gcd = loops.values.reduce { acc, i -> gcd(acc, i) }

        val lcm = loops.values.map { it.toLong() }.reduce { acc, i -> acc*(i/gcd) }

        return lcm
    }

    private fun toNodes(rule: String): Pair<String, Pair<String, String>>
    {
        val (from, left, right) = """([A-Z0-9]{3})""".toRegex().findAll(rule).map { it.value }.toList()
        return from to (left to right)
    }

    private fun gcd(x: Int, y: Int): Int
    {
        return if (y == 0) x else gcd(y, x%y)
    }
}
