package io.github.lunarwatcher.aoc.commons

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

}
