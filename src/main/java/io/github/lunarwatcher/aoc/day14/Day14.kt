package io.github.lunarwatcher.aoc.day14

import io.github.lunarwatcher.aoc.commons.readFile
import io.github.lunarwatcher.aoc.framework.Challenge

class Day14 : Challenge<Int, Int> {
    val int = readFile("day14.txt").first().toInt()

    override fun part1(): Any = process(int)

    /**
     * Part 2 is complete when a recipe starts with the input number is reached
     * The output is the number of recipes before the first index of the recipe that
     * starts with the input number. The examples are slightly misleading for this one;
     * the actual numbers before or after are irrelevant.
     */
    override fun part2(): Any = process2(int)

    override fun processPart1(case: Int): Any = process(case)
    override fun processPart2(case: Int): Any = process2(case)

    private fun process(case: Int) : String {
        val scores = mutableListOf(3, 7)
        var player = 0 to 1

        for(i in 0 until case + 10){
            val toAdd = scores[player.first] + scores[player.second]
            toAdd.toString().map { scores.add(Character.getNumericValue(it)) }
            player = ((player.first + 1 + scores[player.first]) % scores.size) to ((player.second + 1 + scores[player.second]) % scores.size)
        }
        return scores.subList(case, case + 10).joinToString("")

    }

    private fun process2(case: Int) : Int {
        val scores = mutableListOf(3, 7)
        var player = 0 to 1
        val stringCase = case.toString()

        while(stringCase !in scores.takeLast(10).joinToString("")){
            val toAdd = scores[player.first] + scores[player.second]
            toAdd.toString().map { scores.add(Character.getNumericValue(it)) }
            player = ((player.first + 1 + scores[player.first]) % scores.size) to ((player.second + 1 + scores[player.second]) % scores.size)
        }
        return scores.joinToString("").indexOf(stringCase)
    }

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Not used for this day. Pass the int directly instead.")
    override fun processData(input: Int): Int = input;

}