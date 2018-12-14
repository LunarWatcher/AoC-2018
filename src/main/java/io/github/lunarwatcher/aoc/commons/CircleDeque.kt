package io.github.lunarwatcher.aoc.commons

import java.util.*

class CircleDeque<T>(vararg initialElements: T) : ArrayDeque<T>() {
    init {
        addAll(initialElements)
    }

    fun rotate(degrees: Int){
        if(degrees == 0)
            return;
        if(degrees > 0){
            for(i in 0 until degrees){
                addFirst(removeLast())
            }
        }else {
            for(i in 0 until Math.abs(degrees) - 1)
                addLast(remove())
        }
    }

}