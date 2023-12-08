package eu.elfring.adventofcode2023

import eu.elfring.adventofcode2023.days.Day08

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2023 - Day 08")
internal class Day08Test
{
    private val input = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(6, Day08().part1(input))
    }

    private val input2 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(6L, Day08().part2(input2))
    }
}
