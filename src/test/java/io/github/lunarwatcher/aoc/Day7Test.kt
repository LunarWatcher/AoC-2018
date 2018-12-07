package io.github.lunarwatcher.aoc

import io.github.lunarwatcher.aoc.day7.day7
import io.github.lunarwatcher.aoc.day7.day7part1processor
import io.github.lunarwatcher.aoc.day7.day7part2processor
import org.junit.Test

class Day7Test {
    @Test
    fun part1(){
        val case = listOf("Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.",
                "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.",
                "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin.")
        println("Testing...");
        require(::day7part1processor, case, "CABDFE")
        println("It works \\o/")

        day7(false)
    }

    @Test fun part2(){
        println("No verifiable test cases. Going straight for the solution...");
        day7(true)
    }
}