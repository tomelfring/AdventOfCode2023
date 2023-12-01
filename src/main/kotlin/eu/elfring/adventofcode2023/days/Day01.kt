package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle

class Day01 : AoCPuzzle(1)
{
    override fun part1(input: List<String>): Any
    {
        return input.sumOf { line -> line.first { it.isDigit() }.digitToInt()*10 + line.last { it.isDigit() }.digitToInt() }
    }

    override fun part2(input: List<String>): Any
    {

        return input.sumOf { line ->
            //Numbers can overlap one character, oneight -> one & eight, restore first and last letter to map all of them
            val newLine = line
                .replace("one", "o1e")
                .replace("two", "t2o")
                .replace("three", "t3e")
                .replace("four", "f4r")
                .replace("five", "f5e")
                .replace("six", "s6x")
                .replace("seven", "s7n")
                .replace("eight", "e8t")
                .replace("nine", "n9e")

            newLine.first { it.isDigit() }.digitToInt()*10 + newLine.last { it.isDigit() }.digitToInt()
        }
    }
}
