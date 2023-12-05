package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle
import kotlin.math.pow

class Day04 : AoCPuzzle(4)
{
    override fun part1(input: List<String>): Any
    {
        val parsed = parse(input)

        return parsed.sumOf { card ->
            val winningNumbers = card.current.count { it in card.winning }
            if (winningNumbers>0) 2.0.pow(winningNumbers-1).toInt() else 0
        }
    }

    override fun part2(input: List<String>): Any
    {
        val parsed = parse(input).reversed()

        val memory = mutableMapOf<Int, Int>()

        parsed.forEach { card ->
            val winning = card.current.count { it in card.winning }
            val newCards = if (winning > 0) parsed.filter { it.id in card.id+1..card.id+winning } else emptyList()

            memory[card.id] = 1 + newCards.sumOf { memory[it.id]!! }
        }

        return memory.toList().sumOf { it.second }
    }

    private fun parse(input: List<String>): List<Card>
    {
        return input.map {
            val (win, curr) = it.dropWhile { it != ':' }.split("|")

            val id = numberRegex.find(it)!!.value.toInt()
            val a = numberRegex.findAll(win).map { it.value.toInt() }.toList()
            val b = numberRegex.findAll(curr).map { it.value.toInt() }.toList()

            Card(id, a, b)
        }
    }

    data class Card(val id: Int, val winning: List<Int>, val current: List<Int>)

    companion object
    {
        val numberRegex = """(\d+)""".toRegex()
    }
}
