package io.github.lunarwatcher.aoc.day5

import io.github.lunarwatcher.aoc.commons.readFile
import kotlin.streams.asSequence


fun day5part1processor(rawHash: String) : Int{
    // Regex <3
    val pattern = "(\\p{Lu})(?=\\p{Ll})(?i)\\1|(\\p{Ll})(?=\\p{Lu})(?i)\\2"
    val regex = pattern.toRegex()
    @Suppress("NAME_SHADOWING")
    var rawHash = rawHash

    while(regex.containsMatchIn(rawHash))
        rawHash = rawHash.replace(regex, "") // replaceFirst significantly slows it down, but has no impact on the result.

    return rawHash.length
}

fun day5part2processor(rawHash: String) : Int{
    val res = mutableListOf<Int>()
    for(i in 'a'..'z'){
        val regex = "(?i)$i".toRegex();
        res.add(day5part1processor(rawHash.replace(regex, "")))
    }
    return res.sorted().first()
}

fun day5(part: Boolean = false){
    val hash = readFile("day5.txt").joinToString("")
    val res = if(!part) day5part1processor(hash) else day5part2processor(hash)
    println(res)
}