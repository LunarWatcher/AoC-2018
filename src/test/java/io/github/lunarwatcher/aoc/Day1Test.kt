package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day1.day1part1
import io.github.lunarwatcher.aoc.day1.day1part1processor
import io.github.lunarwatcher.aoc.day1.day1part2
import io.github.lunarwatcher.aoc.day1.day1part2processor
import org.junit.Test

class Day1Test {
    @Test
    fun part1(){
        val case1 = listOf(1, 1, 1)
        val case2 = listOf(1, 1, -2)
        val case3 = listOf(-1, -2, -3)

        println("Running test cases...")
        require(::day1part1processor, case1, 3L)
        require(::day1part1processor, case2, 0L)
        require(::day1part1processor, case3, -6L)
        println("Test cases successful")
        print("Your answer is: ")
        day1part1()
    }

    @Test
    fun part2(){
        val case1 = listOf(1, -1)
        val case2 = listOf(3, 3, 4, -2, -4)
        val case3 = listOf(-6, 3, 8, 5, -6)
        val case4 = listOf(7, 7, -2, -7, -4)

        println("Running test cases...")
        require(::day1part2processor, case1, 0L)
        require(::day1part2processor, case2, 10L)
        require(::day1part2processor, case3, 5L)
        require(::day1part2processor, case4, 14L)
        println("Test cases successful.")
        print("Your answer is: ")
        day1part2()

    }
}