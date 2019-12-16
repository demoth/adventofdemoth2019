import java.io.File
import kotlin.math.max

val tasks: Map<String, (Sequence<String>) -> Any> = mapOf(
    "star1" to ::calculateFuel,
    "star2" to ::calculateFuelIncludingFuel
)

fun main(args: Array<String>) {
    val input = File(args.first() + ".txt").readLines()
    val solution = tasks[args.first()]?.invoke(input.asSequence())
    println("Solution for ${args.first()}: $solution")
}

fun calculateFuel(input: Sequence<String>) : Int {
    return input
        .map { it.toInt() }
        .map { requiredFuel(it)}
        .sum()

}

fun calculateFuelIncludingFuel(input: Sequence<String>) : Int {
    return input
        .map { it.toInt() }
        .map { requiredFuelIncludingFuel(it) }
        .sum()
}

fun requiredFuel(mass: Int): Int {
    return max(0, (mass / 3) - 2)
}

fun requiredFuelIncludingFuel(mass: Int): Int {
    return if (mass <= 0) 0
    else requiredFuel(mass) + requiredFuelIncludingFuel(requiredFuel(mass))
}
