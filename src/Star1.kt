import java.io.File

val tasks: Map<String, (List<String>) -> Any> = mapOf(
    "star1" to ::calculateFuel
)

fun main(args: Array<String>) {
    val input = File(args.first() + ".txt").readLines()
    println(tasks[args.first()]?.invoke(input))
}

fun calculateFuel(input: List<String>) : Int {
    return input
        .map { it.toInt() }
        .map { requiredFuel(it)}
        .sum()

}

fun requiredFuel(mass: Int): Int {
    return mass / 3 - 2
}