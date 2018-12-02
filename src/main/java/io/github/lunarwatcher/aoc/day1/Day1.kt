package io.github.lunarwatcher.aoc.day1

import io.github.lunarwatcher.aoc.commons.readFile
import java.lang.RuntimeException

fun day1part1(){
    val ans = day1part1processor(readFile("day1part1.txt", Int::class.java))
    println(ans)
}
fun day1part1processor(nums: List<Int>) : Long {
    var base = 0L

    for(num in nums){
        base += num;
    }

    return base
}

fun day1part2(){
    val ans = day1part2processor(readFile("day1part1.txt", Int::class.java))
    println(ans)
}

fun day1part2processor(nums: List<Int>) : Long{
    var curr = 0L
    val list = mutableListOf(0L)

    while(true) {
        for (num in nums) {
            curr += num
            if (curr !in list) {
                list.add(curr)
            } else return curr;
        }
    }

}
