package eu.elfring.adventofcode2023.days

import eu.elfring.adventofcode2023.AoCPuzzle
import java.util.LinkedList

class Day10 : AoCPuzzle(10) {
    override fun part1(input: List<String>): Any {
        val (startPos, map) = getMap(input)
        val path = resolvePath(startPos, map)

        return path.maxOf { it.value }
    }

    override fun part2(input: List<String>): Any {

        val (startPos, map) = getMap(input)
        val path = resolvePath(startPos, map)

//        map.forEach {
//            it.forEach {
//                print(it.char)
//            }
//            println()
//        }

        // Remove all pipes but the path
        val cleanMap = cleanMap(path, map)

//        cleanMap.forEach {
//            it.forEach {
//                print(it?.char ?: " ")
//            }
//            println()
//        }

        val largeMap = enlarge(cleanMap)

//        largeMap.forEach {
//            println(it.joinToString(""))
//        }

        //Flood fill - Removes everything not enclosed by the pipe
        val queue = LinkedList<Pair<Int, Int>>()
        queue.add(0 to 0)
        while (queue.isNotEmpty())
        {
            val (x, y) = queue.remove()
            largeMap[x][y] = null

            val neighbours = listOfNotNull(
                if (largeMap.getOrNull(x-1)?.getOrNull(y-1) == '.') x-1 to y-1 else null,
                if (largeMap.getOrNull(x  )?.getOrNull(y-1) == '.') x   to y-1 else null,
                if (largeMap.getOrNull(x+1)?.getOrNull(y-1) == '.') x+1 to y-1 else null,
                if (largeMap.getOrNull(x-1)?.getOrNull(y  ) == '.') x-1 to y   else null,
                if (largeMap.getOrNull(x  )?.getOrNull(y  ) == '.') x   to y   else null,
                if (largeMap.getOrNull(x+1)?.getOrNull(y  ) == '.') x+1 to y   else null,
                if (largeMap.getOrNull(x-1)?.getOrNull(y+1) == '.') x-1 to y+1 else null,
                if (largeMap.getOrNull(x  )?.getOrNull(y+1) == '.') x   to y+1 else null,
                if (largeMap.getOrNull(x+1)?.getOrNull(y+1) == '.') x+1 to y+1 else null
            )
            neighbours.forEach { if (!queue.contains(it)) queue.add(it) }
        }

//        println("after")
//        largeMap.forEach {
//            println(it.map { it ?: ' ' }.joinToString(""))
//        }

//        println("after small:")
        var count = 0
        // Count whitespaces in 3*3
        for (y in 1..largeMap.size step 3)
        {
            for (x in 1..largeMap.first().size step 3)
            {
                val char = largeMap[y][x]
//                print(char ?: ' ')
                if (char == '.' || char == '#')
                {
                    count++
                }
            }
//            println()
        }

        return count - path.size
    }

    private fun cleanMap(path: Map<Coord, Int>, originalMap: List<List<Pipe>>): List<List<Pipe?>>
    {
        return originalMap.mapIndexed { y, line ->
            line.mapIndexed { x, pipe ->
                if (path.contains(Coord(x, y)))
                {
                    pipe
                }
                else
                {
                    null
                }
            }
        }
    }

    private fun enlarge(input: List<List<Pipe?>>): MutableList<MutableList<Char?>>
    {
        val enlargedMap = buildList {
            input.forEach { line ->
                val a = line.map {
                    when (it)
                    {

                        Pipe.NORTH_SOUTH -> listOf(".#.", ".#.", ".#.")
                        Pipe.EAST_WEST -> listOf("...", "###", "...")
                        Pipe.NORTH_EAST -> listOf(".#.", ".##", "...")
                        Pipe.NORTH_WEST -> listOf(".#.", "##.", "...")
                        Pipe.SOUTH_WEST -> listOf("...", "##.", ".#.")
                        Pipe.SOUTH_EAST -> listOf("...", ".##", ".#.")
                        Pipe.AIR -> listOf("...", "...", "...")
                        null -> listOf("...", "...", "...")
                    }
                }

                val b = a.reduce { acc, strings ->
                    val (a, b, c) = acc
                    val (d, e, f) = strings

                    listOf(a+d, b+e, c+f)
                }

                this.addAll(b)
            }
        }

        return enlargedMap.map { it.toMutableList() as MutableList<Char?> }.toMutableList()
    }

    private fun resolvePath(startPos: Coord, map: List<List<Pipe?>>): Map<Coord, Int>
    {
        // Note, map is in Y,X format!
        val path = mutableMapOf<Coord, Int>()
        val queue = mutableListOf<Pair<Coord, Int>>()

        queue.add(startPos to 0)
        while (queue.isNotEmpty())
        {
            val (pos, distance) = queue.removeFirst()
            // If previous distance is non existing (new path) or higher (shorter path found)
            val previousDistance = path[pos]
            if (previousDistance == null || previousDistance > distance)
            {
                // Save distance
                path[pos] = distance

                // Add all neighbours
                val possibleNeighbours = getPossibleNeighbours(pos, map)
                possibleNeighbours.forEach {
                    queue.add(it to distance+1)
                }
            }
        }

        return path
    }

    private fun getPossibleNeighbours(pos: Coord, map: List<List<Pipe?>>): List<Coord>
    {
        // Note, map is in Y,X!!
        val (x, y) = pos
        val currentPipe = map[y][x]!!

        val neighbours = mutableListOf<Coord>()
        if (currentPipe in Pipe.northConnections)
        {
            val north = map.getOrNull(y-1)?.getOrNull(x)
            if (north in Pipe.southConnections)
            {
                neighbours.add(Coord(x, y-1))
            }
        }
        if (currentPipe in Pipe.eastConnections)
        {
            val east = map.getOrNull(y)?.getOrNull(x+1)
            if (east in Pipe.westConnections)
            {
                neighbours.add(Coord(x+1,y))
            }
        }
        if (currentPipe in Pipe.southConnections)
        {
            val south = map.getOrNull(y+1)?.getOrNull(x)
            if (south in Pipe.northConnections)
            {
                neighbours.add(Coord(x,y+1))
            }
        }
        if (currentPipe in Pipe.westConnections)
        {
            val west = map.getOrNull(y)?.getOrNull(x-1)
            if (west in Pipe.eastConnections)
            {
                neighbours.add(Coord(x-1, y))
            }
        }

        return neighbours
    }

    private fun getMap(input: List<String>): Pair<Coord, List<List<Pipe>>> {
        val (pos, start) = determineStart(input)

        return pos to input.map { line ->
            line.map { if (it == 'S') start else Pipe.of(it)!! }
        }
    }

    private fun determineStart(input: List<String>): Pair<Coord, Pipe> {
        val (y, line) = input.withIndex().first { (_, s) -> s.contains("S") }
        val (x, _) = line.withIndex().first { it.value == 'S' }

        val north = Pipe.of(input.getOrNull(y - 1)?.getOrNull(x))
        val east = Pipe.of(line.getOrNull(x + 1))
        val west = Pipe.of(line.getOrNull(x - 1))
        val south = Pipe.of(input.getOrNull(y + 1)?.getOrNull(x))

        val northPossible = (north != null && north in Pipe.southConnections)
        val eastPossible = (east != null && east in Pipe.westConnections)
        val southPossible = (south != null && south in Pipe.northConnections)
        val westPossible = (west != null && west in Pipe.eastConnections)

        if (listOf(northPossible, eastPossible, southPossible, westPossible).count { it } != 2) {
            throw IllegalStateException("More than 2 pipes that can connect, more programming needed")
        }

        val pipe = if (northPossible && southPossible)
            Pipe.NORTH_SOUTH
        else if (eastPossible && westPossible)
            Pipe.EAST_WEST
        else if (northPossible && eastPossible)
            Pipe.NORTH_EAST
        else if (northPossible && westPossible)
            Pipe.NORTH_WEST
        else if (southPossible && westPossible)
            Pipe.SOUTH_WEST
        else if (southPossible && eastPossible)
            Pipe.SOUTH_EAST
        else
            throw IllegalStateException("Unknown pipe")

        return Coord(x, y) to pipe
    }

    private enum class Pipe(
        val char: Char
    ) {
        NORTH_SOUTH('|'),
        EAST_WEST('-'),
        NORTH_EAST('L'),
        NORTH_WEST('J'),
        SOUTH_WEST('7'),
        SOUTH_EAST('F'),
        AIR('.');

        companion object {
            val northConnections = listOf(NORTH_SOUTH, NORTH_WEST, NORTH_EAST)
            val eastConnections = listOf(EAST_WEST, SOUTH_EAST, NORTH_EAST)
            val southConnections = listOf(NORTH_SOUTH, SOUTH_WEST, SOUTH_EAST)
            val westConnections = listOf(EAST_WEST, NORTH_WEST, SOUTH_WEST)

            fun of(char: Char?) = entries.find { it.char == char }
        }
    }

    data class Coord(val x: Int, val y: Int)
}
