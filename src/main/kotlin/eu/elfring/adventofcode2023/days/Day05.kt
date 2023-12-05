package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle

class Day05 : AoCPuzzle(5)
{
    override fun part1(input: List<String>): Any
    {
        val seeds = input.first().split(" ").drop(1).map { it.toLong() }
        val (seedsToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation) =
            input.drop(2).joinToString("\n").split("\n\n").map { it.split("\n").drop(1).map { it.split(" ").map { it.toLong() } }.map(::convertToCalculation) }

        val locations = seeds.map { seed ->
            val soil = seedsToSoil.firstOrNull { seed in it.range }?.addition?.let { seed+it } ?: seed
            val fertilizer = soilToFertilizer.firstOrNull { soil in it.range }?.addition?.let { soil+it } ?: soil
            val water = fertilizerToWater.firstOrNull { fertilizer in it.range }?.addition?.let { fertilizer+it } ?: fertilizer
            val light = waterToLight.firstOrNull { water in it.range }?.addition?.let { water+it } ?: water
            val temperature = lightToTemperature.firstOrNull { light in it.range }?.addition?.let { light+it } ?: light
            val humidity = temperatureToHumidity.firstOrNull { temperature in it.range }?.addition?.let { temperature+it } ?: temperature
            humidityToLocation.firstOrNull { humidity in it.range }?.addition?.let { humidity+it } ?: humidity
        }

        return locations.min()
    }

    private fun LongRange.intersectToRange(range: LongRange): Pair<LongRange?, List<LongRange>> {
        // If there is a part in front
        val front = if (this.first < range.first)
        {
            this.first.until(kotlin.math.min(this.last + 1, range.first))
        }
        else null

        // intersection itself
        val min = (kotlin.math.max(this.first, range.first))
        val max = (kotlin.math.min(this.last, range.last))
        val intersection = if (min < max) min..max else null

        // after
        val end = if (this.last > range.last)
        {
            (kotlin.math.max(this.first, range.last + 1))..this.last
        }
        else null

        return intersection to listOfNotNull(front, end)
    }

    override fun part2(input: List<String>): Any
    {
        val seedsInput = input.first().split(" ").drop(1).map { it.toLong() }
        val seeds = seedsInput.chunked(2).map { it[0]..it[0]+it[1] }

        val (seedsToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation) =
            input.drop(2).joinToString("\n").split("\n\n").map { it.split("\n").drop(1).map { it.split(" ").map { it.toLong() } }.map(::convertToCalculation) }

        var matched = mutableListOf<LongRange>()
        var unmatched = seeds
        seedsToSoil.forEach { calculation ->
            unmatched = unmatched.flatMap {
                val (intersect, other) = it.intersectToRange(calculation.range)
                intersect?.let { matched.add((intersect.first+calculation.addition)..(intersect.last+calculation.addition)) }
                other
            }
        }
        unmatched+=matched
        matched = mutableListOf()
        soilToFertilizer.forEach { calculation ->
            unmatched = unmatched.flatMap {
                val (intersect, other) = it.intersectToRange(calculation.range)
                intersect?.let { matched.add((intersect.first+calculation.addition)..(intersect.last+calculation.addition)) }
                other
            }
        }
        unmatched+=matched
        matched = mutableListOf()
        fertilizerToWater.forEach { calculation ->
            unmatched = unmatched.flatMap {
                val (intersect, other) = it.intersectToRange(calculation.range)
                intersect?.let { matched.add((intersect.first+calculation.addition)..(intersect.last+calculation.addition)) }
                other
            }
        }
        unmatched+=matched
        matched = mutableListOf()
        waterToLight.forEach { calculation ->
            unmatched = unmatched.flatMap {
                val (intersect, other) = it.intersectToRange(calculation.range)
                intersect?.let { matched.add((intersect.first+calculation.addition)..(intersect.last+calculation.addition)) }
                other
            }
        }
        unmatched+=matched
        matched = mutableListOf()
        lightToTemperature.forEach { calculation ->
            unmatched = unmatched.flatMap {
                val (intersect, other) = it.intersectToRange(calculation.range)
                intersect?.let { matched.add((intersect.first+calculation.addition)..(intersect.last+calculation.addition)) }
                other
            }
        }
        unmatched+=matched
        matched = mutableListOf()
        temperatureToHumidity.forEach { calculation ->
            unmatched = unmatched.flatMap {
                val (intersect, other) = it.intersectToRange(calculation.range)
                intersect?.let { matched.add((intersect.first+calculation.addition)..(intersect.last+calculation.addition)) }
                other
            }
        }
        unmatched+=matched
        matched = mutableListOf()
        humidityToLocation.forEach { calculation ->
            unmatched = unmatched.flatMap {
                val (intersect, other) = it.intersectToRange(calculation.range)
                intersect?.let { matched.add((intersect.first+calculation.addition)..(intersect.last+calculation.addition)) }
                other
            }
        }

        return (matched+unmatched).minOf { it.min() }
    }

    private fun convertToCalculation(input: List<Long>): Calculation
    {
        val (destinationRangeStart, sourceRangeStart, rangeLength) = input
        return Calculation(sourceRangeStart..sourceRangeStart+rangeLength, destinationRangeStart-sourceRangeStart)
    }

    private data class Calculation(val range: LongRange, val addition: Long)
}

private operator fun <T> List<T>.component6() = get(5)
private operator fun <T> List<T>.component7() = get(6)
private operator fun <T> List<T>.component8() = get(7)
