package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day5.day5
import io.github.lunarwatcher.aoc.day5.day5part1processor
import io.github.lunarwatcher.aoc.day5.day5part2processor
import org.junit.Test

class Day5Test {
    @Test
    fun part1(){
        val case = "dabAcCaCBAcCcaDA"

        println("Running test cases...");
        require(::day5part1processor, case, 10)
        println("Test cases successful.");

        day5()
    }
    @Test
    fun part2(){
        val case = "dabAcCaCBAcCcaDA"

        println("Running test cases...");
        require(::day5part2processor, case, 4)
        println("Test cases successful.");

        day5(true)
    }
}