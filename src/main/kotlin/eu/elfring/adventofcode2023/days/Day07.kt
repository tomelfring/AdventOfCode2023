package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle

class Day07 : AoCPuzzle(7)
{
    override fun part1(input: List<String>): Any
    {
        val sortedCardsWithScore = input.map { parse(it) }.map { getSortOrder(it.first, it.first, false) to it.second }.sortedBy { it.first }
        return sortedCardsWithScore.map { it.second }.reduceIndexed { index, acc, i -> acc+(i*(index+1)) }
    }

    override fun part2(input: List<String>): Any
    {
        val sortedCardsWithScore = input.map { parse(it) }.map {getSortOrder(replaceJokers(it.first), it.first, true) to it.second }.sortedBy { it.first }
        return sortedCardsWithScore.map { it.second }.reduceIndexed { index, acc, i -> acc+(i*(index+1)) }
    }

    private fun parse(input: String): Pair<String, Int>
    {
        val (cards, score) = input.split("""\s+""".toRegex())
        return cards to score.toInt()
    }

    /**
     * Replace the jokers with the most common non joker card
     */
    private fun replaceJokers(input: String): String
    {
        // Special case 5 jokers, it has no other card
        if (input == "JJJJJ")
        {
            return "11111"
        }
        val b = input.groupBy { it }.filterKeys { it != 'J' }.maxBy { it.value.size }

        return input.replace('J', b.key)
    }

    private fun getSortOrder(input: String, orig: String, jokers: Boolean): String
    {
        val grouping = input.groupBy { it }

        val score = if (grouping.size == 1) // five of a kind
        {
            6
        }
        else if (grouping.size == 2)
        {
            if (grouping.any { it.value.size == 4 }) // four of a kind
            {
                5
            }
            else // full house (3+2)
            {
                4
            }
        }
        else if (grouping.size == 3)
        {
            if (grouping.any { it.value.size == 3 }) { // three of a kind
                3
            }
            else // two pair
            {
                2
            }
        }
        else if (grouping.size == 4) // One pair
        {
            1
        }
        else // High card
        {
            0
        }

        // Alphabatically sortable cards:
        val alphabaticallysortable = orig
            .replace("2", "02")
            .replace("3", "03")
            .replace("4", "04")
            .replace("5", "05")
            .replace("6", "06")
            .replace("7", "07")
            .replace("8", "08")
            .replace("9", "09")
            .replace("T", "10")
            .replace("J", if (jokers) "01" else "11")
            .replace("Q", "12")
            .replace("K", "13")
            .replace("Z", "14")

        return "$score$alphabaticallysortable"
    }
}
