package eu.bunburya.hexagons

import java.lang.Math.abs
import kotlin.math.max

class InvalidCoordinatesError(msg: String): Exception(msg)

data class Hex (val q: Int, val r: Int, val s: Int = -q - r) {

    // https://www.redblobgames.com/grids/hexagons/implementation.html#hex

    init {
        require(q + r + s == 0) { "Coordinates must sum to zero" }
    }

    companion object {
        val DIRECTIONS = arrayListOf(
            Hex(1, 0, -1),
            Hex(1, -1, 0),
            Hex(0, -1, 1),
            Hex(-1, 0, 1),
            Hex(-1, 1, 0),
            Hex(0, 1, -1)
        )

        val DIAGONALS = arrayListOf(
            Hex(2, -1, -1),
            Hex(1, -2, 1),
            Hex(-1, -1, 2),
            Hex(-2, 1, 1),
            Hex(-1, 2, -1),
            Hex(1, 1, -2)
        )
    }

    // https://www.redblobgames.com/grids/hexagons/implementation.html#hex-arithmetic

    operator fun plus(other: Hex) = Hex(q + other.q, r + other.r, s + other.s)
    operator fun minus(other: Hex) = Hex(q - other.q, r - other.r, s - other.s)
    operator fun times(k: Int) = Hex(q * k, r * k, s * k)

    // https://www.redblobgames.com/grids/hexagons/implementation.html#hex-distance

    val length: Int get() = ((abs(q) + abs(r) + abs(s)) / 2)
    fun distanceTo(other: Hex): Int = (this - other).length

    fun direction(d: Int): Hex {
        val _d: Int
        if (d !in 0..5) _d = (6 + (d % 6)) % 6
        else _d = d
        return DIRECTIONS[_d]
    }
    fun neighbour(d: Int): Hex = this + direction(d)
    fun diagonalNeighbour(d: Int): Hex = this + DIAGONALS[d]

    // https://www.redblobgames.com/grids/hexagons/implementation.html#rotation

    fun rotateLeft() = Hex(-s, -q, -r)
    fun rotateRight() = Hex(-r, -s, -q)

}