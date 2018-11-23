package beings

import kotlin.random.Random
import kotlin.random.nextInt

class IntDefaultValue(private var range: IntRange, private var default: Int? = null) {
    fun get(): Int {
        return default ?: Random.nextInt(range)
    }
    fun setDefault(default: Int) {
        this.default = default
    }
    fun setRandom(range: IntRange? = null) {
        if (range != null) this.range = range
        this.default = null
    }
}
