package eu.bunburya.hexagons

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * A class representing a hexagon with floating point coordinates (rather than integer coordinates).  Based on the
 * implementation at https://www.redblobgames.com/grids/hexagons/implementation.html#fractionalhex
 *
 * @param q The q component of the cubic (q, r, s) coordinates of the hexagon.
 * @param r The r component of the cubic (q, r, s) coordinates of the hexagon.
 * @param s The s component of the cubic (q, r, s)coordinates of the hexagon.  This is optional; if not provided, it
 * will be calculated based on the constraint q + r + s = 0.
 */

class FractionalHex (val q: Double, val r: Double, val s: Double = -q - r) {

    fun hexRound(): Hex {
        // https://www.redblobgames.com/grids/hexagons/implementation.html#rounding
        var qRounded = q.roundToInt()
        var rRounded = r.roundToInt()
        var sRounded = s.roundToInt()
        val qDiff = abs(qRounded - q)
        val rDiff = abs(rRounded - r)
        val sDiff = abs(sRounded - s)
        if (qDiff > rDiff && qDiff > sDiff) {
            qRounded = -rRounded - sRounded
        } else if (rDiff > sDiff) {
            rRounded = -qRounded - sRounded
        } else {
            sRounded = -qRounded - rRounded
        }
        return Hex(qRounded, rRounded, sRounded)
    }

    // https://www.redblobgames.com/grids/hexagons/implementation.html#line-drawing

    fun hexLerp(other: FractionalHex, t: Double): FractionalHex {
        return FractionalHex(
            q * (1 - t) + other.q * t,
            r * (1 - t) + other.r * t,
            s * (1 - t) + other.s * t
        )
    }

    companion object {
        fun hexLineDraw(a: Hex, b: Hex): ArrayList<Hex> {
            val N = a.distanceTo(b)
            val aNudge = FractionalHex(a.q + 1e-6, a.r + 1e-6, a.s - 2e-6)
            val bNudge = FractionalHex(b.q + 1e-6, b.r + 1e-6, b.s - 2e-6)
            val results = arrayListOf<Hex>()
            val step = 1.0 / max(N, 1)
            for (i in 0..N) {
                results.add(aNudge.hexLerp(bNudge, step * i).hexRound())
            }
            return results
        }
    }

}