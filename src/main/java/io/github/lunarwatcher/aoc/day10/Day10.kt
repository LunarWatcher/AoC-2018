package io.github.lunarwatcher.aoc.day10

import io.github.lunarwatcher.aoc.commons.Vector2i
import io.github.lunarwatcher.aoc.commons.readFile

val regex = "position=<(-?\\d*),(-?\\d*)>velocity=<(-?\\d*),(-?\\d*)>".toRegex();

fun parseVectors(raw: List<String>) : List<Pair<Vector2i, Vector2i>>{
    return raw.map {
        val groups = regex.find(it.replace(" ", ""))!!.groupValues
        val x = groups[1].toInt();
        val y = groups[2].toInt()
        val velX = groups[3].toInt()
        val velY = groups[4].toInt()
        Vector2i(x, y) to Vector2i(velX, velY)
    }
}

fun day10part1processor(raw: List<String>){
    val pairs = parseVectors(raw)

    var lastIndex = 0;
    // This was a literal hell xd Using Int.MAX_VALUE will print too much. Anything over 1000 will in general.
    // These were set based on observations; The final output is generally tiny in span, which means the right
    // answer is likely at one of the time steps with the lowest spread of points. 
    var minXVal = 100;
    var minYVal = 100;

    for(i in 0..10E6.toInt()){
        var minX = Integer.MAX_VALUE
        var minY = Integer.MAX_VALUE
        var maxX = Integer.MIN_VALUE
        var maxY = Integer.MIN_VALUE


        for(pair in pairs){
            pair.first += pair.second
            if(pair.first.x > maxX)
                maxX = pair.first.x;
            if(pair.first.x < minX)
                minX = pair.first.x;
            if(pair.first.y > maxY)
                maxY = pair.first.y;
            if(pair.first.y < minY){
                minY = pair.first.y;
            }

        }
        var printed = false;
        if(maxX - minX < minXVal){
            minXVal = maxX - minX
            lastIndex = i;

            printed = true;
            println(i + 1) // Seconds don't start from 0 (hopefully)
            pairs.print()

        }
        if(maxY - minY < minYVal){
            minYVal = maxY - minY;
            lastIndex = i;
            if(!printed) {

                println(i + 1); // Seconds don't start from 0 (hopefully)
                pairs.print()

            }

        }

        if(i - lastIndex > 30000)
            break;

    }

}

private fun List<Pair<Vector2i, Vector2i>>.print(){
    val mapping = this.sortedWith (compareBy ({ it.first.x }, { it.first.y }))
        .map { it.first }
    val xes = mapping.map { it.x }.sorted()
    val ys = mapping.map { it.y }.sorted()
    val minX = xes.first();
    val maxX = xes.last();

    val minY = ys.first();
    val maxY = ys.last();

    for(y in minY..maxY){
        for(x in minX..maxX){
            print(if(mapping.any { it.x == x && it.y == y }) "#" else ".")
        }
        println()
    }

    println()
}

fun day10(part: Boolean){
    val raw = readFile("day10.txt");
    day10part1processor(raw)
}