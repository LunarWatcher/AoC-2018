package io.github.lunarwatcher.aoc.framework

interface Challenge<I> {
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
    fun processPart1(raw: I) : Any

    /**
     * Processes part 2. Can be used in testing.
     */
    fun processPart2(case: I)

}