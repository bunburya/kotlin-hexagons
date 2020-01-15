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
    }

    // https://www.redblobgames.com/grids/hexagons/implementation.html#hex-arithmetic

    operator fun plus(other: Hex) = Hex(q + other.q, r + other.r, s + other.s)
    operator fun minus(other: Hex) = Hex(q - other.q, r - other.r, s - other.s)
    operator fun times(k: Int) = Hex(q * k, r * k, s * k)

    // https://www.redblobgames.com/grids/hexagons/implementation.html#hex-distance

    val length: Int get() = ((abs(q) + abs(r) + abs(s)) / 2)
    fun distanceTo(other: Hex): Int = (this - other).length

    fun direction(_d: Int): Hex {
        val d: Int
        if (_d !in 0..5) d = (6 + (_d % 6)) % 6
        else d = _d
        return DIRECTIONS[d]
    }
    fun neighbour(d: Int): Hex = this + direction(d)

    // https://www.redblobgames.com/grids/hexagons/implementation.html#rotation

    fun rotateLeft() = Hex(-s, -q, -r)
    fun rotateRight() = Hex(-r, -s, -q)

}