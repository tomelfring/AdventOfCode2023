package eu.elfring.adventofcode2023

import eu.elfring.adventofcode2023.days.Day01

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2023 - Day 01")
internal class Day01Test
{
    private val input = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(142, Day01().part1(input))
    }

    private val input_part2 = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(281, Day01().part2(input_part2))
    }
}
