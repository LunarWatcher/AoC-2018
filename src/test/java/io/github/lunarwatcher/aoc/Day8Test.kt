package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day8.day8
import io.github.lunarwatcher.aoc.day8.day8part1processor
import io.github.lunarwatcher.aoc.day8.day8part2processor
import org.junit.Test

class Day8Test {
    @Test
    fun part1(){
        val case = listOf(
            "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
        )

        println("Running test cases...");
        require(::day8part1processor, case, 138)
        println("Test cases passed.");

        day8(false)
    }

    @Test
    fun part2(){
        val case = listOf(
            "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
        )

        println("Running test cases...");
        require(::day8part2processor, case, 66)
        println("Test cases passed.");

        day8(true)
    }
}