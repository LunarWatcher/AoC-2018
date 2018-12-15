package io.github.lunarwatcher.aoc.day13

import io.github.lunarwatcher.aoc.commons.Vector2i
import io.github.lunarwatcher.aoc.commons.readFile
import io.github.lunarwatcher.aoc.framework.Challenge
import java.lang.IllegalArgumentException


private val comparator = Comparator<GameObject>{ o1, o2 ->
    if (o1.location.y == o2.location.y) {
        o1.location.x - o2.location.x
    } else {
        o1.location.y - o2.location.y
    }
}

enum class ObjectType {
    TURN_ONE, TURN_TWO, INTERSECTION, LINE, SHIP
}

enum class Orientation {
    UP, DOWN, LEFT, RIGHT, NOT_APPLICABLE
}

data class GameMap(val routes: List<GameObject>, val ships: MutableList<GameObject>)

data class GameObject(val location: Vector2i, var orientation: Orientation, val type: ObjectType){
    var currentState: Int = 0
        set(value) {
            field = value % 3;
        }

    fun updateObject(){
        if(type != ObjectType.SHIP)
            return;
        when(orientation){
            Orientation.DOWN -> location.y++;
            Orientation.UP -> location.y--;
            Orientation.RIGHT -> location.x++;
            Orientation.LEFT -> location.x--;
            else -> throw IllegalArgumentException("Illegal orientation for ship: $orientation")
        }

    }

    @Suppress("NON_EXHAUSTIVE_WHEN")
    fun refreshShipOrientation(ship: GameObject){
        require(ship.type == ObjectType.SHIP) { "Can only handle ships!" }
        if(ship.location != location)
            return;
        when(type){
            ObjectType.TURN_ONE -> {
                // /
                when(ship.orientation) {
                    Orientation.DOWN -> ship.orientation = Orientation.LEFT
                    Orientation.UP -> ship.orientation = Orientation.RIGHT
                    Orientation.LEFT -> ship.orientation = Orientation.DOWN
                    Orientation.RIGHT -> ship.orientation = Orientation.UP
                }
            }
            ObjectType.TURN_TWO -> {
                // \\
                when(ship.orientation) {
                    Orientation.DOWN -> ship.orientation = Orientation.RIGHT
                    Orientation.UP -> ship.orientation = Orientation.LEFT
                    Orientation.LEFT -> ship.orientation = Orientation.UP
                    Orientation.RIGHT -> ship.orientation = Orientation.DOWN
                }
            }
            ObjectType.INTERSECTION -> {
                // left -> straight -> right
                when(ship.currentState) {
                    0 -> {
                        when(ship.orientation){
                            Orientation.LEFT -> ship.orientation = Orientation.DOWN
                            Orientation.UP -> ship.orientation = Orientation.LEFT
                            Orientation.DOWN -> ship.orientation = Orientation.RIGHT
                            Orientation.RIGHT -> ship.orientation = Orientation.UP
                        }
                    }
                    // 1 = straight forward => no change. State increments, that's not handled here
                    2 -> {
                        when(ship.orientation){
                            Orientation.UP -> ship.orientation = Orientation.RIGHT
                            Orientation.LEFT -> ship.orientation = Orientation.UP
                            Orientation.DOWN -> ship.orientation = Orientation.LEFT
                            Orientation.RIGHT -> ship.orientation = Orientation.DOWN
                        }
                    }
                }

                ship.currentState += 1
            }
        }
    }

}


class Day13 : Challenge<List<String>, GameMap>{
    private val day13Data = readFile("day13.txt");

    override fun part1(): Any = process(processData(day13Data.toMutableList()), false);
    override fun part2(): Any = process(processData(day13Data.toMutableList()), true);

    override fun processPart1(case: GameMap): Any = process(case, false)

    override fun processPart2(case: GameMap) : Any = process(case, true)

    override fun processData(input: List<String>) : GameMap {
        val ships = mutableListOf<GameObject>()
        val routes = mutableListOf<GameObject>()

        for(y in 0 until input.size){
            val line = input[y]
            for(x in 0 until line.length){
                val char = line[x]
                if(char == ' ')
                    continue;

                val orientation = Orientation.NOT_APPLICABLE
                val position = Vector2i(x, y)
                val type: ObjectType = when (char){
                    '/' -> ObjectType.TURN_ONE

                    '\\' -> ObjectType.TURN_TWO
                    '-', '|' -> ObjectType.LINE

                    '+' -> ObjectType.INTERSECTION
                    // Ships
                    '^' -> {
                        ships.add(GameObject(Vector2i(x, y), Orientation.UP, ObjectType.SHIP))
                        ObjectType.LINE
                    }
                    'v' -> {
                        ships.add(GameObject(Vector2i(x, y), Orientation.DOWN, ObjectType.SHIP))
                        ObjectType.LINE
                    }
                    '>' -> {
                        ships.add(GameObject(Vector2i(x, y), Orientation.RIGHT, ObjectType.SHIP))
                        ObjectType.LINE
                    }
                    '<' -> {
                        ships.add(GameObject(Vector2i(x, y), Orientation.LEFT, ObjectType.SHIP))
                        ObjectType.LINE
                    }
                    else -> throw RuntimeException()
                }

                routes.add(GameObject(position, orientation, type))
            }
        }
        return GameMap(routes, ships);
    }

    private fun process(data: GameMap, part: Boolean) : Vector2i{

        while(true){
            val remove = mutableListOf<GameObject>()
            data.ships.forEach {
                it.updateObject()
                for(ship in data.ships){
                    if(it == ship)
                        continue;
                    if(it.location == ship.location){
                        if(!part)
                            return@process it.location;
                        else {
                            remove.add(it)
                            remove.add(ship)
                            break;
                        }
                    }
                }
                data.routes.forEach { route -> route.refreshShipOrientation(it) }

            }
            data.ships.sortWith(comparator)
            // Avoids ConcurrentModificationException
            if(remove.isNotEmpty()){
                remove.forEach { data.ships.remove(it) }
                remove.clear()
            }
            if(part){
                if(data.ships.size == 1) {
                    val first = data.ships.first()
                    return first.location
                }
            }
        }
    }
}

private fun printMap(data: GameMap){
    val mx = data.routes.maxBy { it.location.x }!!.location.x
    val my = data.routes.maxBy { it.location.y }!!.location.y

    for(y in 0..my){
        for(x in 0..mx){
            val ship = data.ships.firstOrNull { it.location.x == x && it.location.y == y}
            if(ship != null)
                print("x")
            else {
                val route = data.routes.firstOrNull { it.location.x == x && it.location.y == y}
                if(route != null)
                    print(".")
                else print(" ");
            }
        }
        println()
    }
    println()
}
