package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day14.Day14
import org.junit.Test

class Day14Test {
    @Test
    fun part1(){
        require(day::processPart1, 5, "0124515891")
        require(day::processPart1, 2018, "5941429882")

        println("Part 1: ${day.part1()}")
    }

    @Test
    fun part2(){
        println("Part 2: ${day.part2()}")
    }

    companion object {
        val day = Day14();
    }
}