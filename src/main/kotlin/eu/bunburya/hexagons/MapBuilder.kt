package eu.bunburya.hexagons

import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/*  NOTE:  I have not yet implemented the RectangularPointyTopMap (array-based Hex storage) set out at
    https://www.redblobgames.com/grids/hexagons/implementation.html#map-optimized-storage.  Implementation depends on
    the type of loop used to generate the map, and I'm not sure I will want / need array-based storage in any event. */

/**
 * An object with helper functions for building different types of maps of hexagons.  Based on the implementations at
 * https://www.redblobgames.com/grids/hexagons/implementation.html#map-shapes
 */

object MapBuilder {

    //

    // NOTE:  I'm not sure hexFuncs *quite* correctly matches (q,r,s) combinations to map layouts (as described in the
    // link above), but it's "good enough" for now.
    val hexFuncs = hashMapOf (
        "qr" to {q: Int, r: Int -> Hex(q, r)},
        "rq" to {r: Int, q: Int -> Hex(q, r)},
        "qs" to {q: Int, s: Int -> Hex(q, -q - s, s)},
        "sq" to {s: Int, q: Int -> Hex(q, -q - s, s)},
        "rs" to {r: Int, s: Int -> Hex(-r - s, r, s)},
        "sr" to {s: Int, r: Int -> Hex(-r - s, r, s)}
    )

    fun parallelogram(mapHeight: Int, mapWidth: Int, mapOrientation: String = "qr"): MutableSet<Hex> {
        require(mapOrientation in hexFuncs) { "Bad value for mapOrientation" }
        val hexSet = mutableSetOf<Hex>()
        val hexFunc = hexFuncs[mapOrientation]!!
        for (i in 0 until mapWidth) {
            for (j in 0 until mapHeight) {
                hexSet.add(hexFunc(i, j))
            }
        }
        return hexSet
    }

    fun triangle(mapSize: Int): MutableSet<Hex> {
        val hexSet = mutableSetOf<Hex>()
        for (q in 0..mapSize) {
            for (r in 0..mapSize - q) {
                hexSet.add(Hex(q, r))
            }
        }
        return hexSet
    }


    fun hexagon(mapRadius: Int): MutableSet<Hex> {
        val hexSet = mutableSetOf<Hex>()
        var r1: Int
        var r2: Int
        for (q in -mapRadius..mapRadius) {
            r1 = max(-mapRadius, -q - mapRadius)
            r2 = min(mapRadius, -q + mapRadius)
            for (r in r1..r2) {
                hexSet.add(Hex(q, r))
            }
        }
        return hexSet
    }

    fun rectangle(mapHeight: Int, mapWidth: Int, mapOrientation: String): MutableSet<Hex> {
        require(mapOrientation in hexFuncs) { "Bad value for mapOrientation" }
        val hexSet = mutableSetOf<Hex>()
        val hexFunc = hexFuncs[mapOrientation]!!
        var offset: Int
        for (i in 0..mapHeight) {
            offset = floor(i/2.0).toInt()
            for (j in -offset until mapWidth - offset) {
                hexSet.add(hexFunc(i, j))
            }
        }
        return hexSet
    }

}