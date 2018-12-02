package io.github.lunarwatcher.aoc.day2

import io.github.lunarwatcher.aoc.commons.readFile
import java.lang.RuntimeException

fun day2(part: Boolean){

    val ids = readFile("day2part1.txt", String::class.java)
    val res = if(!part) day2part1processor(ids) else day2part2processor(ids)
    println(res)
}

fun day2part1processor(ids: List<String>) : Long{
    var twice = 0L
    var thrice = 0L

    for(id in ids){
        val letters = id.map { it }
            .groupingBy { it }
            .eachCount()
            .map { (k, v) -> v }
            .distinct()
        for(count in letters){
            if(count == 2) twice++;
            if(count == 3) thrice++;
        }
    }
    if(thrice == 0L && twice == 0L)
        return 0;
    if(twice == 0L) twice = 1; // 0-multiplication protection
    if(thrice == 0L) thrice = 1; // 0-multiplication protection
    return twice * thrice
}

fun day2part2processor(ids: List<String>) : String{
    for(i in 0 until ids.size){
        val iItem = ids[i].map { it }
        inner@for(j in 0 until ids.size){
            if(j == i) continue;
            var diff = 0
            val jItem = ids[j].map { it }
            iItem.zip(jItem).forEach it@{(first, second) ->
                if(first != second)
                    diff++;
                if(diff == 2)
                    return@it;
            }
            if(diff == 1)
                return iItem.filterIndexed { index, char -> char == jItem[index]}.joinToString("")
        }
    }
    throw RuntimeException("Nein!");
}