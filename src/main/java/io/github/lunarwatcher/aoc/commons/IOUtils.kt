@file:Suppress("UNCHECKED_CAST")

package io.github.lunarwatcher.aoc.commons

import jdk.nashorn.api.scripting.URLReader
import java.io.BufferedReader
import java.lang.RuntimeException


fun readFile(loc: String) = readFile(loc, String::class.java)

fun <T> readFile(loc: String, cls: Class<T>) : List<T>{
    val list = mutableListOf<T>()
    val file = Thread.currentThread().contextClassLoader.getResource(loc)
    val reader = BufferedReader(URLReader(file))
    val lines = reader.readLines()
    for (line in lines){
        list.add(processItem(cls, line))
    }
    return list
}

fun <F, S> readFile(loc: String, first: Class<F>, second: Class<S>) : List<Pair<F, S>>{
    val list = mutableListOf<Pair<F, S>>()
    val file = Thread.currentThread().contextClassLoader.getResource(loc)
    val reader = BufferedReader(URLReader(file))

    val lines = reader.readLines().map { it.replace(" ", "").split(",") }

    for(pair in lines){
        if(pair.size != 2)
            throw RuntimeException("Size must be 2.");
        val one = processItem(first, pair[0])
        val two = processItem(second, pair[1]);

        list.add(one to two)
    }
    return list;
}

private fun <T> processItem(cls: Class<T>, raw: String) : T{
    if(cls == String::class.java){
        return raw as T;
    } else if(cls == Int::class.java) {
        return raw.toInt() as T
    } else if(cls == Float::class.java){
        return raw.toFloat() as T
    } else if(cls == Double::class.java) {
        return raw.toDouble() as T
    }
    throw IllegalArgumentException("Unsupported type: ${cls.name}")
}