package eu.elfring.adventofcode2023

import eu.elfring.adventofcode2023.days.Day10

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Year 2023 - Day 10")
internal class Day10Test
{
    private val input = """
        7-F7-
        .FJ|7
        SJLL7
        |F--J
        LJ.LJ
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 1")
    fun part1()
    {
        Assertions.assertEquals(8, Day10().part1(input))
    }

    // 10 tiles
//    val input2 = """
//        FF7FSF7F7F7F7F7F---7
//        L|LJ||||||||||||F--J
//        FL-7LJLJ||||||LJL-77
//        F--JF--7||LJLJ7F7FJ-
//        L---JF-JLJ.||-FJLJJ7
//        |F|F-JF---7F7-L7L|7|
//        |FFJF7L7F-JF7|JL---7
//        7-L-JL7||F7|L7F-7F7|
//        L.L7LFJ|||||FJL7||LJ
//        L7JLJL-JLJLJL--JLJ.L
//    """.trimIndent().split("\n")

    val input2 = """
        ..........
        .S------7.
        .|F----7|.
        .||.J..||.
        .||....||.
        .|L-7F-J|.
        .|.J||..|.
        .L--JL--J.
        ..........
    """.trimIndent().split("\n")

    @Test
    @DisplayName("Part 2")
    fun part2()
    {
        Assertions.assertEquals(4, Day10().part2(input2))
    }
}
