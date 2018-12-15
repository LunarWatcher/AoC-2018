@file:Suppress("UNCHECKED_CAST")

package io.github.lunarwatcher.aoc.day15

import io.github.lunarwatcher.aoc.commons.Vector2i
import io.github.lunarwatcher.aoc.commons.readFile
import io.github.lunarwatcher.aoc.framework.Challenge
import java.util.*

interface Tile
class Wall : Tile{
    override fun toString() = "#"
}
class Floor : Tile{
    override fun toString() = "."
}
data class Entity(val type: Char, var health: Int = 200, val damage: Int = 3): Tile {
    fun hit(attacker: Entity) {
        health -= attacker.damage;
    }
    override fun toString() = type.toString()
}
data class PathDist(val dist: Int, val previousPos: Vector2i? = null)

class Day15 : Challenge<List<String>, MutableMap<Vector2i, Tile>> {
    val input = readFile("day15.txt")

    override fun part1(): Any = process(processData(input))

    override fun part2(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun processPart1(case: MutableMap<Vector2i, Tile>) = process(case)

    override fun processPart2(case: MutableMap<Vector2i, Tile>): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun process(map: MutableMap<Vector2i, Tile>) : Int {
        var turns = 0;
        thread@while(true){

            val entities = map.filter { (pos, tile) -> tile is Entity && tile.health > 0 }.toSortedMap(compareBy({it.y},{it.x})) as SortedMap<Vector2i, Entity>
            for((entityPos, entity) in entities){
                if(entity.health <= 0 || map[entityPos] !is Entity)
                    continue;
                val enemies =  (map.filter { (pos, tile) -> tile is Entity }.toSortedMap(compareBy({it.y},{it.x})) as SortedMap<Vector2i, Entity>)
                    .filter { (pos, it) -> entity.type != it.type && entity.health > 0 }
                if(enemies.isEmpty())
                    break@thread
                var nearbyEnemies = getNearbyCoords(entityPos)
                    .filter { enemies.containsKey(it) }
                    .map { it to map[it]!! } as List<Pair<Vector2i, Entity>>
                // The current position. Stored as a variable in case the move method updates it.
                var currPos = entityPos;
                if(nearbyEnemies.isEmpty()){
                    val possiblePositions = enemies.map { getNearbyCoords(it.key).filter { s -> map[s] is Floor } }.flatten()
                    val positions = possiblePositions.map { it to (dijkstra(entityPos, it, map)?.size ?: Int.MAX_VALUE) }
                        .filter { it.second < Int.MAX_VALUE && it.second != 0}
                        .sortedBy { it.second }
                    if(positions.isNotEmpty()){
                        // Grabs the minimum distance
                        val minDists = positions.minBy { it.second }!!.second
                        // Grabs the target position, using a comparator that checks for order, that the entity moves to
                        val selected = positions.filter { it.second == minDists }.map { it.first }
                            .sortedWith(compareBy({it.y},{it.x}))
                            .first()
                        val step = dijkstra(entityPos, selected, map)!!.first()
                        map[entityPos] = Floor()
                        map[step] = entity
                        currPos = step;
                    }
                }

                // Refresh the nearby enemies
                nearbyEnemies = getNearbyCoords(currPos)
                    .filter { enemies.containsKey(it) }
                    .map { it to (map[it]!! as Entity) }

                if(nearbyEnemies.isNotEmpty()){
                    val victim = nearbyEnemies.sortedWith(compareBy({it.first.y},{it.first.x}))
                        .sortedBy { it.second.health }.first()
                    if(victim.second.health <= 0)
                        throw RuntimeException("That's not gonna work")
                    victim.second.hit(entity)
                    if(victim.second.health <= 0)
                        map[victim.first] = Floor()
                }
            }

            turns++;
        }

        return turns * map.filter { it.value is Entity }.map { (it.value as Entity).health }.sum()
    }

    override fun processData(input: List<String>): MutableMap<Vector2i, Tile> {
        val map = mutableMapOf<Vector2i, Tile>()

        for(y in 0 until input.size){
            val line = input[y]
            for(x in 0 until line.length){
                val obj = line[x]
                val tile = if(obj == '#') Wall()
                        else if(obj == '.') Floor()
                        else if(obj == 'G') Entity(obj)
                        else if(obj == 'E') Entity(obj)
                        else throw RuntimeException("Failed to parse letter $obj")
                map[Vector2i(x, y)] = tile
            }
        }

        return map;
    }

    private fun dijkstra(origin: Vector2i, target: Vector2i, map: Map<Vector2i, Tile>) : List<Vector2i>? {
        val visited = mutableListOf<Vector2i>()
        val unvisited = mutableListOf<Vector2i>()
        val dists = mutableMapOf<Vector2i, PathDist>()
        var currentPos = origin;
        dists[currentPos] = PathDist(0)

        while(true){
            getNearbyCoords(currentPos)
                .filter { map[it] is Floor }
                .filter { it !in unvisited && it !in visited}
                .forEach { unvisited.add(it) }
            unvisited.forEach {
                val dist = dists[currentPos]!!.dist + 1
                if(dist < dists[it]?.dist ?: Integer.MAX_VALUE)
                    dists[it] = PathDist(0, currentPos)
            }
            unvisited.remove(currentPos)
            visited.add(currentPos)

            if(currentPos == target){
                val route = mutableListOf(target)
                var dist = dists[target]!!
                while(dist.previousPos != null){
                    route.add(dist.previousPos!!)
                    dist = dists[dist.previousPos!!]!!

                }
                return route.reversed()
                    .drop(1)
            }
            currentPos = unvisited.minBy { dists[it]!!.dist } ?: return null
        }
    }
}

fun Map<Vector2i, Tile>.print(){
    val sorted = this.toSortedMap(compareBy({it.y}, {it.x}))

    for(y in 0 .. sorted.lastKey().y){
        for(x in 0 .. sorted.lastKey().x){
            print(sorted[Vector2i(x, y)])
        }
        println()
    }
    println()
}

private fun getNearbyCoords(pos: Vector2i) : List<Vector2i> {
    return listOf(pos.addAndCopy(1, 0), pos.addAndCopy(-1, 0),
        pos.addAndCopy(0, 1), pos.addAndCopy(0, -1))

}