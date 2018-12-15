package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day15.Day15
import org.junit.Test

class Day15Test {
    @Test
    fun part1(){
        require(day::processPart1,
            day.processData(listOf("#######",
                "#.G...#",
                "#...EG#",
                "#.#.#G#",
                "#..G#E#",
                "#.....#",
                "#######")),
            27730)
        require(day::processPart1,
            day.processData(listOf("#######",
                    "#G..#E#",
                    "#E#E.E#",
                    "#G.##.#",
                    "#...#E#",
                    "#...E.#",
                    "#######")), 36334)
        require(day::processPart1,
            day.processData(listOf("#######",
                    "#E..EG#",
                    "#.#G.E#",
                    "#E.##E#",
                    "#G..#.#",
                    "#..E#.#",
                    "#######")), 39514)

        println("Part 1: ${day.part1()}")
    }

    companion object {
        val day = Day15()
    }
}