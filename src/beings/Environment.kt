package beings

import kotlin.random.Random
import kotlin.random.nextInt

typealias Constraint = (Being) -> Int


class RandomEllipseConstraint: (Being) -> Int {

    private val geneName = ('A'..'z').map { it }.shuffled().subList(0, 2).joinToString("")
    private val id = Allele.defaultValue.get()
    private val reward =
        Random.nextInt(-20..20) * (id * id) + Random.nextInt(-20..20) * id + Random.nextInt(-20..20)

    override fun invoke(being: Being): Int {
        val allele = being.findAlleleByGeneName(geneName)
        if (allele?.id == id) {
            return reward
        }
        return 0
    }
}

class Environment(items: List<Constraint>) : ImmutableList<Constraint>(items) {

    constructor(): this((1..20).map { RandomEllipseConstraint() })

    fun apply(being: Being) = items.map {
        val fitness = it(being)
        being.lastFitnessValue = fitness
        return@map fitness
    }.sum()
}

