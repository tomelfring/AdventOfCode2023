package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle
import kotlin.math.abs
import kotlin.math.exp

class Day11 : AoCPuzzle(11)
{
    override fun part1(input: List<String>): Any
    {
        val galaxies = getGalaxies(input)

        val expandedGalaxies = galaxies.map { it.withExpansion(1) }

        return getDistances(expandedGalaxies)
    }

    override fun part2(input: List<String>): Any
    {
        val galaxies = getGalaxies(input)

        val expandedGalaxies = galaxies.map { it.withExpansion(999_999) }

       return getDistances(expandedGalaxies)
    }

    private fun getGalaxies(input: List<String>): List<Galaxy>
    {
        // Calculate empty rows & columns
        val emptyRows = input.map { row -> row.all { it == '.' } }
        val emptyColumns = input.first().indices.map { column ->
            input.all { it[column] == '.' }
        }

        var tempOffset = 0
        val rowOffsets = emptyRows.map { if (it) ++tempOffset else tempOffset }
        tempOffset = 0
        val columnOffsets = emptyColumns.map { if (it) ++tempOffset else tempOffset }

        return buildList {
            input.forEachIndexed { y, row ->
                row.forEachIndexed { x, c ->
                    if (c == '#')
                    {
                        this.add(Galaxy(Point(x.toLong(), y.toLong()), columnOffsets[x], rowOffsets[y]))
                    }
                }
            }
        }
    }

    private fun getDistances(galaxies: List<Galaxy>): Long
    {
        val galaxyPairs = galaxies.map { it to galaxies-it }

        return galaxyPairs.sumOf {
            val (from, tos) = it
            tos.sumOf { it.position.distanceTo(from.position) }
        } / 2
    }

    private data class Galaxy(
        val position: Point,
        val xOffset: Int,
        val yOffset: Int
    )
    {
        fun withExpansion(cosmicExpansionFactor: Int) = Galaxy(this.position.withOffset(xOffset, yOffset, cosmicExpansionFactor), xOffset, yOffset)
    }

    private data class Point(
        val x: Long,
        val y: Long
    )
    {
        fun distanceTo(other: Point) = abs(other.x-this.x) + abs(other.y-this.y)
        fun withOffset(xOffset: Int, yOffset: Int, cosmicExpansionFactor: Int) = Point(x+xOffset*cosmicExpansionFactor, y+yOffset*cosmicExpansionFactor)
    }
}
