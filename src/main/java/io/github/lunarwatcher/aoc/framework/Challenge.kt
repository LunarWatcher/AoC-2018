package io.github.lunarwatcher.aoc.framework

interface Challenge<I, PF> {
    /**
     * Runs the actual part 1
     */
    fun part1() : Any

    /**
     * Runs the actual part 2
     */
    fun part2() : Any

    /**
     * Processes part 1. Can be used in testing.
     */
    fun processPart1(case: PF) : Any

    /**
     * Processes part 2. Can be used in testing.
     */
    fun processPart2(case: PF) : Any

    fun processData(input: I) : PF

}