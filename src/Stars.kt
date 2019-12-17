import java.io.File
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.math.max

val tasks: Map<String, (Sequence<String>) -> Any> = mapOf(
    "star1" to ::calculateFuel,
    "star2" to ::calculateFuelIncludingFuel,
    "star3" to ::runOpcode,
    "star4" to ::findNounAndVerb
)

fun main(args: Array<String>) {
    val input = File(args.first() + ".txt").readLines()
    val solution = tasks[args.first()]?.invoke(input.asSequence())
    println("Solution for ${args.first()}: $solution")
}

fun calculateFuel(input: Sequence<String>): Int {
    return input
        .map { it.toInt() }
        .map { requiredFuel(it) }
        .sum()

}

fun calculateFuelIncludingFuel(input: Sequence<String>): Int {
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

fun runOpcode(input: Sequence<String>): Int {
    val memory = input.flatMap { it.split(",").asSequence() }.map { it.toInt() }.toMutableList()
    var i = 0
    while (true) {
        if (i >= memory.size)
            throw IllegalStateException("Segmentation fault")
        when (memory[i]) {
            1 -> {
                memory[memory[i + 3]] = memory[memory[i + 1]] + memory[memory[i + 2]]
                i += 4
            }
            2 -> {
                memory[memory[i + 3]] = memory[memory[i + 1]] * memory[memory[i + 2]]
                i += 4
            }
            99 -> return memory[0]
            else -> throw IllegalStateException("Unexpected opcode '${memory[i]}'")
        }
    }
}

fun findNounAndVerb(input: Sequence<String>): Pair<Int, Int> {
    val initialMemory = input.flatMap { it.split(",").asSequence() }.toList()
    var workingMemory: ArrayList<String>
    for (noun in 0..99) {
        for (verb in 0..99) {
            workingMemory = ArrayList(initialMemory)
            workingMemory[1] = noun.toString()
            workingMemory[2] = verb.toString()
            val result = try {
                runOpcode(workingMemory.asSequence())
            } catch (e: Exception) {
                0
            }
            if (result == 19690720) {
                return Pair(noun, verb)
            }

        }
    }
    throw IllegalStateException("Did not compute")
}