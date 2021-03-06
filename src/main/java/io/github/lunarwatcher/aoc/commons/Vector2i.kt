package io.github.lunarwatcher.aoc.commons

import java.util.*

data class Vector2i(var x: Int, var y: Int){

    infix operator fun plusAssign(other: Vector2i){
        this.x += other.x;
        this.y += other.y;
    }
    infix operator fun minusAssign(other: Vector2i){
        this.x -= other.x;
        this.y -= other.y;
    }

    infix operator fun timesAssign(other: Vector2i){
        this.x *= other.x;
        this.y *= other.y;
    }

    infix operator fun divAssign(other: Vector2i){
        this.x /= other.x;
        this.y /= other.y;
    }

    override fun equals(other: Any?): Boolean {
        if(other !is Vector2i)
            return false;

        else return x == other.x && y == other.y;
    }

    fun addAndCopy(xAdd: Int, yAdd: Int) = Vector2i(x + xAdd, y + yAdd);
}
