package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day2.day2
import io.github.lunarwatcher.aoc.day2.day2part1processor
import io.github.lunarwatcher.aoc.day2.day2part2processor
import org.junit.Test

class Day2Test {
    @Test
    fun part1(){
        val case = listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")

        println("Running test cases...")
        require(::day2part1processor, case, 12L)
        println("Test cases successful.");

        day2(false)
    }

    @Test
    fun part2(){
        val case = listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")

        println("Running test cases...")
        require(::day2part2processor, case, "fgij")
        println("Test cases successful.")

        day2(true)
    }
}