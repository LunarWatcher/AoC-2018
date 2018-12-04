package io.github.lunarwatcher.aoc

fun <T: List<*>, R : Any> require(func: (T) -> R, data: T, expected: R){
    val actual = func(data)
    require(actual == expected) {
        System.err.println("Assertion failed: $actual != $expected")
        "Test case failed!"
    }
}