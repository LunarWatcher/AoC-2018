package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day9.day9
import io.github.lunarwatcher.aoc.day9.day9part1processor
import org.junit.Test

class Day9Test {
    @Test
    fun part1(){
        val case1 = listOf("10 players; last marble is worth 1618 points")
        val case2 = listOf("13 players; last marble is worth 7999 points")
        val case3 = listOf("17 players; last marble is worth 1104 points")
        val case4 = listOf("21 players; last marble is worth 6111 points")
        val case5 = listOf("30 players; last marble is worth 5807 points")

        println("Running test cases...");
        require(::day9part1processor, case1, 8317);
        require(::day9part1processor, case2, 146373)
        require(::day9part1processor, case3, 2764)
        require(::day9part1processor, case4, 54718);
        require(::day9part1processor, case5, 37305)
        println("Test cases successful!");

        day9(false);
    }

    @Test
    fun part2(){
        day9(true)
    }
}