package io.github.lunarwatcher.aoc.day12

import io.github.lunarwatcher.aoc.commons.readFile
import io.github.lunarwatcher.aoc.framework.Challenge
import java.lang.UnsupportedOperationException

data class Day12Data (val initialState: String, val modifiers: Map<String, Boolean>)

class Day12 : Challenge<List<String>> {
    private val data = parseInput(readFile("day12.txt"))

    override fun part1(): Any = process(data, 20)

    override fun part2(): Any{
        val initial100 = process(data, 100)
        val continued200 = process(data, 200)
        val diff = continued200 - initial100

        return initial100 + (50000000000 - 100) / 100L * diff
    }

    override fun processPart1(raw: List<String>): Any = process(parseInput(raw), 20)

    override fun processPart2(case: List<String>) {
        throw UnsupportedOperationException("No tests allowed for part 2 of this day.")
    }

    private fun parseInput(raw: List<String>): Day12Data {
        val initialState = raw[0].split(": ")[1]
        val modifiers = mutableMapOf<String, Boolean>()

        for (i in 2 until raw.size) {
            val item = raw[i].split(" => ")
            modifiers[item[0]] =
                    item[1].map { it == '#' }.also { if (it.size != 1) throw IllegalArgumentException() }[0]
        }

        return Day12Data(initialState, modifiers)
    }

    private fun process(data: Day12Data, gens: Long): Long {
        var currState = ".....${data.initialState}..."
        var zeroCoord = 5;

        for (generation in 0 until gens) {
            val builder = StringBuilder()
            for (i in 2 until currState.length - 2) {
                val subset = currState.substring(i - 2, i + 3)

                if (data.modifiers.keys.contains(subset)) {
                    if (data.modifiers[subset]!!)
                        builder.append("#")
                    else builder.append(".");
                } else builder.append(".");
            }

            val current = builder.toString();
            zeroCoord += 3;
            currState = ".....$current..." // Add more pots to make it possible to deal with big generations

        }

        return currState.countPlants(zeroCoord);
    }
}


private fun String.countPlants(zeroCoord: Int) =
    this.mapIndexed{ i, item ->
        if(item == '#')
            (i - zeroCoord).toLong()
        else
            0
    }.sum()
