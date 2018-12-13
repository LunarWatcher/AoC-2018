package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day12.Day12
import org.junit.Test

class Day12Test {
    @Test
    fun part1(){
        val case = listOf(
            "initial state: #..#.#..##......###...###",
                    "",
                    "...## => #",
                    "..#.. => #",
                    ".#... => #",
                    ".#.#. => #",
                    ".#.## => #",
                    ".##.. => #",
                    ".#### => #",
                    "#.#.# => #",
                    "#.### => #",
                    "##.#. => #",
                    "##.## => #",
                    "###.. => #",
                    "###.# => #",
                    "####. => #"
        )



        require(day::processPart1, day.processData(case), 325L)

        println("Part 1: ${day.part1()}")
    }

    @Test
    fun part2(){
        println("Part 2: ${day.part2()}")
    }

    companion object {
        private val day = Day12()
    }

}