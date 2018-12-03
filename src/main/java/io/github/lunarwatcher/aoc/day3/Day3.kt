package io.github.lunarwatcher.aoc.day3

import io.github.lunarwatcher.aoc.commons.readFile

fun day3(part: Boolean = false){
    val rawObjects = readFile("day3.txt")

    val res = if(!part) day3part1processor(rawObjects) else day3part2processor(rawObjects)
    println(res)
}

fun day3part1processor(rawObjects: List<String>) : Int {
    val map: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()

    rawObjects.forEach {
        val spaces = it.split(" ").map { it.replace("[@#:]".toRegex(), "")}.filter { !it.isEmpty() && !it.isBlank()}
        val id = spaces[0].toInt()
        val xy = spaces[1].split(",")
        val x = xy[0].toInt()
        val y = xy[1].toInt()

        val wh = spaces[2].split("x")
        val w = wh[0].toInt()
        val h = wh[1].toInt()

        for(i in x until (x + w)){
            if(map[i] == null) map[i] = mutableMapOf()
            for(j in y until (y + h)){
                if(map[i]!![j] == null){
                    map[i]!![j] = id
                }else {
                    map[i]!![j] = -1;
                }
            }
        }
    }

    return map.map { (_, child) ->
        child.map { (k, v) -> v}.filter { it == -1 }.count()
    }.filter { it != 0}.sum()

}

fun day3part2processor(rawObjects: List<String>) : Int {
    // <X, <Y, <ID>>>
    val map: MutableMap<Int, MutableMap<Int, MutableList<Int>>> = mutableMapOf()

    rawObjects.forEach {
        val spaces = it.split(" ").map { it.replace("[@#:]".toRegex(), "")}.filter { !it.isEmpty() && !it.isBlank()}
        val id = spaces[0].toInt()
        val xy = spaces[1].split(",")
        val x = xy[0].toInt()
        val y = xy[1].toInt()

        val wh = spaces[2].split("x")
        val w = wh[0].toInt()
        val h = wh[1].toInt()

        for(i in x until (x + w)){
            if(map[i] == null) map[i] = mutableMapOf()
            for(j in y until (y + h)){
                if(map[i]!![j] == null){
                    map[i]!![j] = mutableListOf()
                }
                map[i]!![j]!!.add(id)
            }
        }
    }
    val badIds = mutableListOf<Int>()
    val r = map.map { (x, child) ->
        x to child.map { (y, ids) ->
            y to if (ids.size == 1 && ids[0] !in badIds) ids[0] else {
                for (id in ids) if (id !in badIds) badIds.add(id)
                -1
            }
        }.filter { (k, v) -> v != -1 && v !in badIds }
    } .map { (k, v) -> v.map { it.second } }.flatMap { it }.distinct().filter { it !in badIds}

    return r.first();

}