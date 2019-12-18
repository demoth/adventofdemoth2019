import java.io.File
import java.lang.IllegalStateException
import kotlin.math.abs


typealias Point = Pair<Int, Int>

fun main() {
    println(distanceToClosest(findIntersections(File("wires.txt").readLines())))
}

fun distanceToClosest(points: Collection<Point>): Int = points.map { abs(it.first) + abs(it.second) }.min()!!

fun findIntersections(input: List<String>): Collection<Point> {
    val wire0 = convertToPoints(input[0].split(","))
    val wire1 = convertToPoints(input[1].split(","))
    return wire0.intersect(wire1)
}

fun convertToPoints(cmds: Collection<String>): Collection<Point> {
    var position = Point(0, 0)
    return cmds.flatMap { cmd ->
        val result = arrayListOf<Point>()
        val distance = cmd.substring(1).toInt()
        val step: (Point) -> Point = when (cmd.first()) {
            'R' -> { p -> Point(p.first + 1, p.second) }
            'U' -> { p -> Point(p.first, p.second + 1) }
            'D' -> { p -> Point(p.first, p.second - 1) }
            'L' -> { p -> Point(p.first - 1, p.second) }
            else -> throw IllegalStateException("Unknown command found: $cmd")
        }
        repeat(distance) {
            position = step(position)
            result.add(position)
        }
        result
    }.toSet()
}