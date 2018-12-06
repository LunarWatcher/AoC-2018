package io.github.lunarwatcher.aoc.day6

import io.github.lunarwatcher.aoc.commons.readFile
import kotlin.math.absoluteValue

const val INFINITE_AREA = -1
data class Area (val center: Pair<Int, Int>, val points: MutableList<Pair<Int, Int>> = mutableListOf()){
    val area: Int
        get() = points.size

    fun won(x: Int, y: Int){
        points.add(x to y)
    }
}


fun day6part1processor(coords: List<Pair<Int, Int>>) : Int{
    val areas = getAreas(coords)

    val filteredAreas = areas.filter {
        it.points.none {
            it.first == 0 || it.second == 0
        } // it's safe to assume if x or y == 0, the area is infinite.
    }

    return filteredAreas.maxBy { it.area }!!.area
}

fun day6part2processor(points: List<Pair<Int, Int>>) : Int {
    val areas = getAreas(points)
    val manhattenPoints = areas.map { it.points}.flatten()
    val maxDist = 10000

    val accepted = mutableListOf<Int>()

    for((x, y) in manhattenPoints){
        var totalDist = 0;
        for(area in areas){
            totalDist += calculateManhatten(x, y, area.center)
        }

        if(totalDist < maxDist)
            accepted.add(totalDist)
    }

    return accepted.size
}

fun calculateManhatten(x: Int, y: Int, center: Pair<Int, Int>) : Int{
    return (center.first - x).absoluteValue + (y - center.second).absoluteValue
}

fun getAreas(coords: List<Pair<Int, Int>>) : List<Area> {
    val areas = coords.map { Area(it) }

    val max = coords.maxBy { it.first }!!.first to coords.maxBy { it.second }!!.second
    val grid = mutableMapOf<Int, Pair<Int, Int>>()
    for (x in 0..(max.first)){
        for(y in 0..(max.second)){
            var highestManhatten: Int = Int.MAX_VALUE
            var currArea: Area? = null
            for(area in areas){
                val center = area.center
                val manhatten = calculateManhatten(x, y, center)
                if(manhatten < highestManhatten){
                    highestManhatten = manhatten
                    currArea = area
                } else if(manhatten == highestManhatten){
                    // The point is equally far from two points; skip it.
                    continue;
                }
            }
            currArea!!.won(x, y)
        }
    }

    return areas;
}

fun day6(part: Boolean = false){
    val points = readFile("day6.txt", Int::class.java, Int::class.java)
    val res = if(!part) day6part1processor(points) else day6part2processor(points)
    println(res)
}