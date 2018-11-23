package beings


class Being(private val karyotype: Karyotype) {

    var lastFitnessValue: Int = 0

    init {
        InstanceCounter.count(javaClass)
    }

    constructor() : this(Karyotype())

    fun mate(being: Being): Being {
        return Being(karyotype.mate(being.karyotype))
    }

    fun mutate() {
        karyotype.mutate()
    }

    fun findAlleleByGeneName(geneName: String) = karyotype.findAlleleByGeneName(geneName)

    override fun toString(): String {
        return "works $lastFitnessValue"
    }
}
