package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day6.day6
import io.github.lunarwatcher.aoc.day6.day6part1processor
import org.junit.Test

class Day6Test {
    @Test
    fun part1(){
        val case = listOf(
            1 to 1,
            1 to 6,
            8 to 3,
            3 to 4,
            5 to 5,
            8 to 9
        )

        println("Running test cases...");
        require(::day6part1processor, case, 17)
        println("Test cases passed.")

        day6()
    }

    @Test fun part2(){
        println("No proper test cases. Running instantly.");

        day6(true)
    }
}