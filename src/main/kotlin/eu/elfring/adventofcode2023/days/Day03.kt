package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle

class Day03 : AoCPuzzle(3)
{
    override fun part1(input: List<String>): Any
    {
        val grid = input.map { it.toCharArray() }
        var sum = 0L

        input.forEachIndexed { y, line ->
            var charBuffer = StringBuffer()
            var valid = false
            line.forEachIndexed { x, char ->
                if (char.isDigit())
                {
                    // Add numbers to buffer
                    charBuffer.append(char)
                    // Check if potentially valid
                    val a = grid.getOrNull(y-1)
                    val b = line
                    val c = grid.getOrNull(y+1)
                    if (a?.getOrNull(x-1)?.isSymbol() == true ||
                        a?.getOrNull(x)?.isSymbol() == true ||
                        a?.getOrNull(x+1)?.isSymbol() == true ||
                        b.getOrNull(x-1)?.isSymbol() == true ||
                        // char itself
                        b.getOrNull(x+1)?.isSymbol() == true ||
                        c?.getOrNull(x-1)?.isSymbol() == true ||
                        c?.getOrNull(x)?.isSymbol() == true ||
                        c?.getOrNull(x+1)?.isSymbol() == true)
                    {
                        valid = true
                    }
                }
                else
                {
                    if (valid)
                    {
                        sum+=charBuffer.toString().toInt()
                    }
                    charBuffer = StringBuffer()
                    valid = false
                }
            }
            if (charBuffer.isNotEmpty() && valid)
            {
                sum+=charBuffer.toString().toInt()
            }
        }
        return sum
    }

    private fun Char.isSymbol(): Boolean
    {
        return !this.isDigit() && this!='.'
    }

    override fun part2(input: List<String>): Any
    {
        val grid = input.map { it.toCharArray() }
        var sum = 0
        grid.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '*')
                {
                    // Determine adjecent numbers
                    var adjecentCount = 0
                    var value = 1

                    val a = grid.getOrNull(y-1)
                    val c = grid.getOrNull(y+1)

                    //line above
                    var skipMiddle = false
                    var skipRight = false
                    if (a?.getOrNull(x-1)?.isDigit() == true)
                    {
                        adjecentCount++
                        skipMiddle = true
                        if (a.getOrNull(x)?.isDigit()==true)
                        {
                            skipRight = true
                        }
                        value *= resovleNumber(a, x-1)
                    }
                    if (!skipMiddle && a?.getOrNull(x)?.isDigit() == true)
                    {
                        adjecentCount++
                        skipRight = true
                        value *= resovleNumber(a, x)
                    }
                    if (!skipRight && a?.getOrNull(x+1)?.isDigit() == true)
                    {
                        adjecentCount++
                        value *= resovleNumber(a, x+1)
                    }

                    // same line
                    if (line.getOrNull(x-1)?.isDigit() == true)
                    {
                        adjecentCount++
                        value *= resovleNumber(line, x-1)
                    }
                    if (line.getOrNull(x+1)?.isDigit() == true)
                    {
                        adjecentCount++

                        value *= resovleNumber(line, x+1)
                    }

                    //line under
                    skipMiddle = false
                    skipRight = false
                    if (c?.getOrNull(x-1)?.isDigit() == true)
                    {
                        adjecentCount++
                        skipMiddle = true

                        if (c.getOrNull(x)?.isDigit()==true)
                        {
                            skipRight = true
                        }

                        value *= resovleNumber(c, x-1)
                    }
                    if (!skipMiddle && c?.getOrNull(x)?.isDigit() == true)
                    {
                        adjecentCount++
                        skipRight = true

                        value *= resovleNumber(c, x)
                    }
                    if (!skipRight && c?.getOrNull(x+1)?.isDigit() == true)
                    {
                        adjecentCount++

                        value *= resovleNumber(c, x+1)
                    }

                    if (adjecentCount == 2)
                    {
                        sum+=value
                    }
                }
            }
        }
        return sum
    }

    fun resovleNumber(line: CharArray, xInit: Int): Int
    {
        // move left as far as needed
        var x = xInit
        while (line.getOrNull(x-1)?.isDigit() == true)
        {
            x--
        }
        val sb = StringBuffer()
        while (line.getOrNull(x)?.isDigit() == true)
        {
            sb.append(line[x])
            x++
        }
        return sb.toString().toInt()
    }
}
