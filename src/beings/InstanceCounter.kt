package beings

/**
 * Singleton to count number of instances
 */
object InstanceCounter {

    private val stats = mutableMapOf<Class<Any>, Int>()

    fun count(klass: Class<Any>) {
        stats[klass] = stats.getOrPut(klass) { 0 } + 1
    }

    fun show() {
        for ((klass, nbr) in stats) {
            println("$klass : $nbr")
        }
    }
}
