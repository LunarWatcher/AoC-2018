@file:Suppress("UNCHECKED_CAST")

package io.github.lunarwatcher.aoc.commons

import jdk.nashorn.api.scripting.URLReader
import java.io.BufferedReader


fun readFile(loc: String) = readFile(loc, String::class.java)

fun <T> readFile(loc: String, cls: Class<T>) : List<T>{
    val list = mutableListOf<T>()
    val file = Thread.currentThread().contextClassLoader.getResource(loc)
    val reader = BufferedReader(URLReader(file))

    if(cls == String::class.java){
        list.addAll(reader.readLines() as List<T>)
    } else if(cls == Int::class.java){
        val lines = reader.readLines()
        for(line in lines){
            list.add(line.toInt() as T)
        }
    } else throw IllegalArgumentException("Unsupported class type. You should probably fix that :>");

    return list
}