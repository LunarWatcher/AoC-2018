package io.github.lunarwatcher.aoc.day7

import io.github.lunarwatcher.aoc.commons.readFile
import kotlin.math.absoluteValue


fun day7part1processor(raw: List<String>) : String{
    val letters = raw.map {
        val split = it.split(" ").filter { it.length == 1 }
        split[0][0] to split[1][0]
    }
    // Char, dependent chars
    val map = mutableMapOf<Char, MutableList<Char>>()
    letters.forEach { (char, child) ->
        if(map[char] == null) map[char] = mutableListOf(child)
        else map[char]!!.add(child)
    }

    val resultBuilder = StringBuilder()

    while(true) {
        val freeChars = mutableMapOf<Char, Boolean>()
        freeChars.putAll(map.map { it.key to true }.toMap())

        for ((char, dependents) in map) {
            for ((freeChar, _) in freeChars.filter { it.value }) {
                if (freeChar in dependents) {
                    freeChars[freeChar] = false;
                }
            }
        }
        val char = freeChars.filter { it.value == true }.keys.sorted().first()
        map[char]!!.let {
            for(c in it){
                if(map[c] == null)
                    map[c] = mutableListOf()
            }
        }
        map.remove(char)
        resultBuilder.append(char)
        if(map.isEmpty())
            break;

    }
    return resultBuilder.toString()
}

fun day7part2processor(raw: List<String>) : Int {
    val letters = raw.map {
        val split = it.split(" ").filter { it.length == 1 }
        split[0][0] to split[1][0]
    }
    // Char, dependent chars
    val map = mutableMapOf<Char, MutableList<Char>>()
    letters.forEach { (char, child) ->
        if(map[char] == null) map[char] = mutableListOf(child)
        else map[char]!!.add(child)
    }

    var workers = 0
    var time = -1
    val workMapping = mutableMapOf<Char, Int>()
    while(map.size > 0){
        with (workMapping.filter { it.value == time }.keys.sorted()){
            forEach {
                workMapping.remove(it)
            }
            workers -= size;
            for(char in this) {
                map[char]!!.let {
                    for(c in it){
                        if(map[c] == null) {
                            map[c] = mutableListOf()
                        }
                    }
                }
                map.remove(char)
            }
        }
        val freeChars = mutableMapOf<Char, Boolean>()
        freeChars.putAll(map.map { it.key to true }.toMap())

        for ((char, dependents) in map) {
            for ((freeChar, _) in freeChars.filter { it.value }) {
                if (freeChar in dependents || freeChar in workMapping) {
                    freeChars[freeChar] = false;
                }
            }
        }
        with(freeChars.filter { it.value == true }.keys.toList().sorted()){
            take(5 - workers).also { workers += it.size }
                .forEach {char ->
                    workMapping[char] = time + (char - 'A' + 61) // char - char = int.
                }
        }
        time++;
    }
    return time
}

fun day7(part: Boolean){
    val inp = readFile("day7.txt");
    @Suppress("IMPLICIT_CAST_TO_ANY")
    val res = if(!part) day7part1processor(inp) else day7part2processor(inp)
    println(res);
}