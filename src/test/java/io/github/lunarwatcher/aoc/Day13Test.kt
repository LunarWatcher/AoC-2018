package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.commons.Vector2i
import io.github.lunarwatcher.aoc.day13.Day13
import org.junit.Test

class Day13Test{
    @Test
    fun part1(){
        val case = listOf("/->-\\        ",
                "|   |  /----\\",
                "| /-+--+-\\  |",
                "| | |  | v  |",
                "\\-+-/  \\-+--/",
                "  \\------/ ")

        require(day::processPart1, day.processData(case), Vector2i(7,3))

        println("Part 1: ${day.part1()}")
    }

    @Test
    fun part2(){
        val case = listOf("/>-<\\  ",
                "|   |  ",
                "| /<+-\\",
                "| | | v",
                "\\>+</ |",
                "  |   ^",
                "  \\<->/")

        require(day::processPart2, day.processData(case), Vector2i(6, 4))

        println("Part 2: ${day.part2()}")
    }

    companion object {
        private val day = Day13()
    }
}