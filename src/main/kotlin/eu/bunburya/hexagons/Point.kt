package eu.bunburya.hexagons

/**
 * A simple class representing a point with (x, y) coordinates.  Also supports iteration.
 */

data class Point (val x: Double, val y: Double): Iterable<Double> {
    override fun iterator() = iterator{
        yield(x)
        yield(y)
    }
}