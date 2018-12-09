package io.github.lunarwatcher.aoc.day9

import io.github.lunarwatcher.aoc.commons.CircleDeque
import io.github.lunarwatcher.aoc.commons.readFile

/*
Rules:
    - Marbles start at 0
    - Increase by 1 until all have a value > 0
    - Lowest-numbered marble is replaced between 1 and 2-marbles
        - Exception on first turn.
        - Eventually it adds between the current marble and the one that was just placed)
    - 23 adds to the score
        - Also removes the marble that's 7 marbles counter-clockwise from the current marble
        - The current marble is the recently placed one.
 */

private fun parseInput(raw: List<String>) : Pair<Int, Long> = raw.first()
    .split(" ")
    .filter { it.toLongOrNull() != null }
    .let { it[0].toInt() to it[1].toLong() }

private fun unifiedProcessor(raw: List<String>, part2: Boolean) : Long {
    val data = parseInput(raw);
    val players = data.first
    val highestValue = (data.second + 1) * if(part2) 100 else 1

    val circle = CircleDeque(0L)
    val scores = Array(players) { 0L }

    for(marbleValue in 1..highestValue){
        if(marbleValue % 23 == 0L){
            circle.rotate(-7)
            // marbleValue % players makes it wrap around.
            scores[(marbleValue % players).toInt()] += marbleValue + circle.pop()
        }else {
            circle.rotate(2)
            circle.addLast(marbleValue)
        }
    }
    return scores.sortedDescending().first()
}

fun day9part1processor(raw: List<String>) = unifiedProcessor(raw, false)

fun day9part2processor(raw: List<String>) = unifiedProcessor(raw, true)

fun day9(part: Boolean){
    val data = readFile("day9.txt");
    val res = if(!part) day9part1processor(data) else day9part2processor(data)
    println(res);
}