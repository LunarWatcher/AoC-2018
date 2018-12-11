package io.github.lunarwatcher.aoc.day11

import io.github.lunarwatcher.aoc.commons.Vector2i
import io.github.lunarwatcher.aoc.commons.readFile
import java.util.*

data class ElevenPart2(val coords: Vector2i, val size: Int, val sum: Int)

private fun parseData(serialNumber: Int) : SortedMap<Vector2i, Int>{
    val powerLevels = mutableMapOf< Vector2i, Int>()

    for (x in 1..300){
        for (y in 1..300){
            val rackId = x + 10;
            val powerLevelStr = (((rackId * y) + serialNumber) * rackId).toString()
            val powerLevel = powerLevelStr[powerLevelStr.length - 3].toString().toInt() - 5

            powerLevels[Vector2i(x, y)] = powerLevel

        }
    }

    return powerLevels.toSortedMap(compareBy({it.x}, {it.y}))
}

fun day11part1processor(serialNumber: Int) : Pair<Int, Int>{
    val sortedMap = parseData(serialNumber);

    val powerLevelsSummed = mutableMapOf<Int, Pair<Int, Int>>()

    for(x in 2..299){
        for(y in 2..299){
            val area = mutableListOf<Int>()

            for(i in -1..1){
                for(j in -1..1){
                    area.add(sortedMap[Vector2i(x + i, y + j)]!!)
                }
            }
            powerLevelsSummed[area.sum()] = x - 1 to y - 1
        }
    }
    // Sorted = descending
    val finalSortedMap = powerLevelsSummed.toSortedMap()

    return finalSortedMap[finalSortedMap.lastKey()]!!
}

fun day11part2processor(serialNumber: Int) : ElevenPart2 {
    val sortedMap = parseData(serialNumber);

    var highest = ElevenPart2(Vector2i(0, 0), 0, 0)

    // It's unrealistic for t to be 1 or > 20, especially considering the sizes of part 1

    for(size in 2..20){
        var highestArea = 0
        var coords = Vector2i(0, 0)
        for(x in 1..(300 - size)){
            for(y in 1..(300 - size)){
                var area: Int = 0
                for(i in 0 until size){
                    for(j in 0 until size){
                        area += sortedMap[Vector2i(x + i, y + j)]!!
                    }
                }
                if(area > highestArea){
                    highestArea = area;
                    coords = Vector2i(x, y)
                }
            }
        }
        if(highestArea > highest.sum){
            highest = ElevenPart2(coords, size, highestArea)
        }
    }
    return highest;
}

fun day11(part: Boolean){
    val data = readFile("day11.txt").first().toInt()
    println(if(!part) day11part1processor(data) else day11part2processor(data))

}