package lib

import org.junit.jupiter.params.converter.TypedArgumentConverter

class IntArrayArg : TypedArgumentConverter<String, Array<Int>>(
    String::class.java, Array<Int>::class.java
) {
    override fun convert(source: String?) =
        source.orEmpty().toArray { toInt() }
}

class IntMatrixArg : TypedArgumentConverter<String, Array<Array<Int>>>(
    String::class.java, Array<Array<Int>>::class.java
) {
    override fun convert(source: String?) =
        source.orEmpty().toArray { toArray { toInt() } }
}

class StringArrayArg : TypedArgumentConverter<String, Array<String>>(
    String::class.java, Array<String>::class.java
) {
    override fun convert(source: String?) =
        source.orEmpty().toArray { this.removeSurrounding("'") }
}

@Suppress("CouldBeSequence")
inline fun <reified T> String.toArray(
    parseElement: String.() -> T
): Array<T> =
    if (matches("\\[\\s*]".toRegex())) emptyArray()
    else this.require { it.startsWith('[') && it.endsWith(']') }
        .removeSurrounding("[", "]")
        .splitPossiblyNestedArray(',')
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map(parseElement)
        .toTypedArray()

fun String.splitPossiblyNestedArray(c: Char) =
    if (any { it in "[]" }) // nested array
        split("]$c").map { if (it.endsWith(']')) it else "$it]" }
    else
        split(c)
