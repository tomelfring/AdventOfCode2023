package eu.elfring.adventofcode2023

import eu.elfring.adventofcode2023.days.Day15

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2023 - Day 15")
internal class Day15Test
{
    private val input = """
        TODO INPUT
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(0, Day15().part1(input))
    }

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(0, Day15().part2(input))
    }
}
