package beings

import kotlin.random.Random


class Gene(val name: String) {
    init {
        InstanceCounter.count(javaClass)
    }

    constructor(): this(('a'..'z').map { it }.shuffled().subList(1, 2).joinToString(""))
}

class Allele(val gene: Gene, var id: Int) {

    init {
        InstanceCounter.count(javaClass)
    }

    constructor() : this(Gene()) // Same gene but random id

    constructor(gene: Gene) : this(gene, defaultValue.get())  // random id

    fun sameGene(other: Allele): Boolean {
        return this.gene === other.gene
    }

    fun mate(allele: Allele): Allele {
        return Allele(gene, if (Random.nextBoolean()) id else allele.id)
    }

    fun mutate() {
        if (mutationFactor.get() == 0) {
            mutationNumber++
            this.id = defaultValue.get()
        }
    }

    companion object {
        val mutationFactor = IntDefaultValue(0..1000)
        val defaultValue = IntDefaultValue(0..9)
        var mutationNumber = 0
    }
}


class Chromosome(alleles: List<Allele>) : ImmutableList<Allele>(alleles) {

    init {
        InstanceCounter.count(javaClass)
    }

    constructor(size: Int) : this((1..size).map { Allele() })
    constructor(chromosome: Chromosome) : this(chromosome.map { Allele(it.gene) })

    fun isSame(chromosome: Chromosome): Boolean {
        for ((thisAllele, otherAllele) in this zip chromosome) {
            if (!thisAllele.sameGene(otherAllele)) return false
        }
        return true
    }

    fun mate(chromosome: Chromosome): Chromosome {
        return Chromosome(this.zip(chromosome) { a, b -> a.mate(b)})
    }

    fun mutate() {
        items.map { it.mutate() }
    }

    fun findAlleleByGeneName(name: String) = items.find { it.gene.name == name }
}


class ChromosomePair(private val chromosome1: Chromosome, private val chromosome2: Chromosome) :
    ImmutableList<Chromosome>(listOf(chromosome1, chromosome2)) {

    init {
        InstanceCounter.count(javaClass)
    }

    private constructor(chromosome: Chromosome) : this(chromosome, Chromosome(chromosome))
    constructor() : this(Chromosome(defaultSize.get()))

    init {
        if (!isSame()) throw RuntimeException("Can't pair not similar chromosomes")
    }

    private fun isSame() = chromosome1.isSame(chromosome2)

    fun mate(chromosomePair: ChromosomePair): ChromosomePair {
        return ChromosomePair(
            chromosome1.mate(chromosomePair.chromosome1),
            chromosome2.mate(chromosome2)
        )
    }

    fun mutate() {
        for (chromosome in items) {
            chromosome.mutate()
        }
    }

    fun findAlleleByGeneName(geneName: String): Allele? {
        for (chromosome in items) {
            val allele = chromosome.findAlleleByGeneName(geneName)
            if (allele != null) return allele
        }
        return null
    }

    companion object {
        val defaultSize = IntDefaultValue(0..9, 5)
    }
}



class Karyotype(chromosomes: List<ChromosomePair>) : ImmutableList<ChromosomePair>(chromosomes) {

    init {
        InstanceCounter.count(javaClass)
    }

    constructor() : this((1..defaultSize.get()).map { ChromosomePair() })

    fun mate(karyotype: Karyotype): Karyotype {
        return Karyotype(this.zip(karyotype) { a, b -> a.mate(b) })
    }

    fun mutate() {
        for (chromosomePair in items) {
            chromosomePair.mutate()
        }
    }

    fun findAlleleByGeneName(geneName: String): Allele? {
        for (chromosomePair in items) {
            val allele = chromosomePair.findAlleleByGeneName(geneName)
            if (allele != null) return allele
        }
        return null
    }

    companion object {
        val defaultSize = IntDefaultValue(0..9, 5)
    }
}
