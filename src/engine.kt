import beings.*

const val BEING_NBR = 100


fun main(args: Array<String>) {
    ChromosomePair.defaultSize.setDefault(3)
    Karyotype.defaultSize.setDefault(5)
    Allele.defaultValue.setRandom(0..9)

    val environment = Environment()

    var beings = (1..BEING_NBR).map { Being() }

    for (i in 0..1000) {
        if (i % 100 == 0) {
            println(i)
            println(mean(beings))
        }
        beings += beings.map { it.mate(beings.random()) }
        beings.sortedBy { environment.apply(it) }
        beings.forEach { it.mutate() }
        beings = beings.slice(0..BEING_NBR)
    }
    println(Allele.mutationNumber)

    InstanceCounter.show()
}


fun mean(beings: List<Being>): Int {
    return beings.map { it.lastFitnessValue }.sum() / beings.size
}
