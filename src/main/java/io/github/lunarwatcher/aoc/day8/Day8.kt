package io.github.lunarwatcher.aoc.day8

import io.github.lunarwatcher.aoc.commons.readFile
import java.util.*

data class Node(val children: List<Node>, val metadata: List<Int>){
    val childCount: Int
        get() = children.size

    val hasChildren: Boolean
        get() = children.size > 0
    val hasMetadata: Boolean
        get() = metadata.size > 0
}

fun day8part1processor(raw: List<String>) : Int{
    val stack = loadStack(raw);

    /*
    Header (childrenCount, metadataCount) => children if childrenCount > 0 => metadata if metadataCount > 0
     */

    val nodes = parseNodes(stack);
    return nodes.map { sumNodePart1(it) }.sum()
}

fun day8part2processor(raw: List<String>) : Int {
    val stack = loadStack(raw);
    val nodes = parseNodes(stack)
    return nodes.map { sumNodePart2(it) }.sum()
}

fun loadStack(raw: List<String>) = LinkedList<Int>().also {
    raw.first().split(" ")
        .forEach {str ->
            it.add(str.toInt())
        }

    if(it.size == 0)
        throw RuntimeException();
}
fun sumNodePart1(node: Node) : Int {
    var sum = 0;
    sum += node.metadata.sum()

    if(node.children.isNotEmpty()){
        for(child in node.children){
            sum += sumNodePart1(child)

        }
    }
    return sum;
}

fun sumNodePart2(node: Node) : Int {
    var sum = 0;
    if(node.hasChildren && node.hasMetadata){
        val children = node.children;
        val metadata = node.metadata;

        for(index in metadata){
            if(children.size <= index - 1)
                continue;
            val child = children[index - 1]
            if(child.hasMetadata && child.hasChildren)
                sum += sumNodePart2(child)
            else sum += child.metadata.sum();
        }
    }

    return sum;
}

fun parseNodes(stack: LinkedList<Int>, nested: Boolean = false) : List<Node> {
    val baseNodes = mutableListOf<Node>()
    while(stack.size > 0){
        val childrenCount = stack.pollFirst()
        val metadataCount = stack.pollFirst()
        val children = if(childrenCount == 0) listOf<Node>() else {
            val l = mutableListOf<Node>()
            for(i in 0 until childrenCount) {
                l.addAll(parseNodes(stack, true));
            }
            l
        }
        val metadata = if(metadataCount == 0) listOf<Int>() else {
            val l = mutableListOf<Int>()

            for(i in 0 until metadataCount){
                l.add(stack.pollFirst());
            }
            l;
        }
        baseNodes.add(Node(children, metadata))
        if(nested){
            return baseNodes
        }

    }
    return baseNodes;
}

fun day8(part: Boolean){
    val data = readFile("day8.txt");
    val res = if(!part) day8part1processor(data) else day8part2processor(data)
    println(res);
}