package beings

open class ImmutableList<T>(protected val items: List<T>) : List<T> {

    override val size: Int
        get() = items.size

    override fun contains(element: T) = items.contains(element)

    override fun containsAll(elements: Collection<T>) = items.containsAll(elements)

    override fun get(index: Int) = items[index]

    override fun indexOf(element: T) = items.indexOf(element)

    override fun isEmpty() = items.isEmpty()

    override fun iterator() = items.iterator()

    override fun lastIndexOf(element: T) = items.lastIndexOf(element)

    override fun listIterator() = items.listIterator()

    override fun listIterator(index: Int) = items.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int) = items.subList(fromIndex, toIndex)
}
