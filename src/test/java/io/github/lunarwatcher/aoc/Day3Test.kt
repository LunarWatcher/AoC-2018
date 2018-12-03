package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day3.day3
import io.github.lunarwatcher.aoc.day3.day3part1processor
import io.github.lunarwatcher.aoc.day3.day3part2processor
import org.junit.Test

class Day3Test{
    @Test
    fun part1(){
        val case = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        )
        println("Running test cases...")
        require(::day3part1processor, case, 4)
        println("Test cases passed.")

        day3()
    }

    @Test
    fun part2(){
        val case = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        )
        println("Running test cases...")
        require(::day3part2processor, case, 3)
        println("Test cases passed.")

        day3(true)
    }
}