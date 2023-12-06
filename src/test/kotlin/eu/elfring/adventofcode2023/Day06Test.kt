package eu.elfring.adventofcode2023

import eu.elfring.adventofcode2023.days.Day06

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2023 - Day 06")
internal class Day06Test
{
    private val input = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(288, Day06().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(71503, Day06().part2(input))
    }
}
