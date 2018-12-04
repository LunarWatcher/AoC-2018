package io.github.lunarwatcher.aoc.day4

import io.github.lunarwatcher.aoc.commons.readFile
import io.github.lunarwatcher.aoc.day1.day1part2processor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

enum class LogAction {
    WAKE, ASLEEP, BEGIN;

    companion object {
        fun of(str: String) : LogAction = when(str) {
            "wakes up" -> WAKE
            "falls asleep" -> ASLEEP
            else -> BEGIN
        }
    }
}

data class Shift(val id: Int, var startTime: Instant? = null, var endTime: Instant? = null)

val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("GMT+0"))
fun parseEntries(log: List<String>) : List<Shift> {
    val logEntries = mutableListOf<Shift>()
    var id = -1
    var activeShift: Shift? = null
    for(entry in log){

        val time = LocalDateTime.parse(entry.substring(1, 17), dateTimeFormatter).toInstant(ZoneOffset.ofHours(0))

        val action = LogAction.of(entry.split(" ", limit=3)[2])
        if(id == -1 && action != LogAction.BEGIN){
            throw RuntimeException("Looks like ya fucked that one up!");
        }

        if(action == LogAction.BEGIN){
            id = entry.split(" ")[3].replace("[# ]".toRegex(), "").toInt()
            activeShift?.let { logEntries.add(it.also { it.endTime = time.also { it.minusMillis(60000)} }) } // Assuming one minute less here.
        } else if(action == LogAction.ASLEEP){
            activeShift = Shift(id, time, null)
        } else if(action == LogAction.WAKE){
            logEntries.add(activeShift!!.also {
                it.endTime = time.also { it.minusMillis(60000)};
            })
            activeShift = null
        }
    }

    return logEntries
}

fun day4part1processor(log: List<String>): Int {
    var sortedLog = log.sortedBy { it.substring(1, 17) }

    val logEntries = parseEntries(sortedLog)
    val flatMap = logEntries.map {
        it.id to ((it.endTime!!.toEpochMilli() - it.startTime!!.toEpochMilli()) / 60000f).toLong()
    }
    val resMap = mutableMapOf<Int, Long>()

    flatMap.forEach { (id, time) ->
        if(resMap[id] == null) resMap[id] = 0;
        resMap[id] = resMap[id]!!.plus(time)
    }

    val pair = resMap.toList().sortedByDescending {
        it.second
    }.first()
    val id = pair.first
    val applicable = logEntries.filter { it.id == id }
    // <Minute, count>
    val minutes = mutableMapOf<Int, Int>()
    for(shift in applicable){

        val start = LocalDateTime.ofInstant(shift.startTime!!, ZoneId.of("GMT+0")).minute
        val end = LocalDateTime.ofInstant(shift.endTime!!, ZoneId.of("GMT+0")).minute
        for (i in start until end){
            if(minutes[i] == null) minutes[i] = 1;
            else{
                minutes[i] = minutes[i]!!.plus(1)
            };
        }
    }

    return id * minutes.toList()
        .sortedByDescending {
            it.second
        }.first().first
}

fun day4part2processor(log: List<String>): Int {
    var sortedLog = log.sortedBy { it.substring(1, 17) }

    val logEntries = parseEntries(sortedLog)
    val flatMap = logEntries.map {
        it.id to ((it.endTime!!.toEpochMilli() - it.startTime!!.toEpochMilli()) / 60000f).toLong()
    }
    val resMap = mutableMapOf<Int, Long>()

    flatMap.forEach { (id, time) ->
        if(resMap[id] == null) resMap[id] = 0;
        resMap[id] = resMap[id]!!.plus(time)
    }

    val res = resMap.toList().map { (id, _) ->
        val applicable = logEntries.filter { it.id == id }
        // <Minute, count>
        val minutes = mutableMapOf<Int, Int>()
        for(shift in applicable){

            val start = LocalDateTime.ofInstant(shift.startTime!!, ZoneId.of("GMT+0")).minute
            val end = LocalDateTime.ofInstant(shift.endTime!!, ZoneId.of("GMT+0")).minute
            for (i in start until end){
                if(minutes[i] == null) minutes[i] = 1;
                else{
                    minutes[i] = minutes[i]!!.plus(1)
                };
            }
        }
        id to minutes
    }

    // <Minute, <ID, Count>>
    val result = mutableMapOf<Int, MutableMap<Int, Int>>()

    for ((forId, minutes) in res){
        for((minute, count) in minutes) {
            if (result[minute] == null) result[minute] = mutableMapOf()
            result[minute]!!.put(forId, count)
        }
    }
    // minute, <ID, count>
    val map = mutableMapOf<Int, Pair<Int, Int>>()
    for(i in 1..60){
        var max: Int = 0
        var id: Int = 0;
        result[i]?.forEach { (iId, count) ->
            if(count > max) {
                id = iId
                max = count;
            }

        } ?: continue;
        map[i] = id to max
    }
    val list = map.toList().sortedByDescending { (_, idCount) ->
        idCount.second
    }
    val match = list.first()
    return match.first * match.second.first
}


fun day4(day: Boolean = false){
    val logs = readFile("day4.txt")
    println(if(!day) day4part1processor(logs) else day4part2processor(logs))
}