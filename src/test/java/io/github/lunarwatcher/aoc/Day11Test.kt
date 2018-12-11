package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day11.day11
import io.github.lunarwatcher.aoc.day11.day11part1processor
import org.junit.Test

class Day11Test {
    @Test
    fun part1(){
        require(::day11part1processor, 18, Pair(33,45))
        day11(false);
    }

    @Test
    fun part2(){

        day11(true);
    }

}