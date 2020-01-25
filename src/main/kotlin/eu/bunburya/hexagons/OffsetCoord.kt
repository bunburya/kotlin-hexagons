package eu.bunburya.hexagons

enum class Offset(val value: Int) {
    EVEN (1),
    ODD (-1);

    operator fun times(other: Int) = this.value * other
    operator fun plus(other: Int) = this.value + other
}

/**
 * A class representing offset coordinates of a hexagon, rather than cubic coordinates.  Based on the implementation at
 * https://www.redblobgames.com/grids/hexagons/implementation.html#offset
 *
 * @param col  The column in which the hexagon is located.
 * @param row  The row in which the hexagon is located.
 */

data class OffsetCoord (val col: Int, val row: Int) {

    // https://www.redblobgames.com/grids/hexagons/implementation.html#offset

    companion object {

        fun qOffsetFromCube(offset: Offset, h: Hex) = OffsetCoord(
            h.q,
            h.r + ((h.q + offset * (h.q.and(1))) / 2)
        )
        fun rOffsetFromCube(offset: Offset, h: Hex) = OffsetCoord (
            h.q + ((h.r + offset * (h.r.and(1))) / 2),
            h.r
        )

        fun qOffsetToCube(offset: Offset, oc: OffsetCoord) = Hex(
            oc.col,
            oc.row - ((oc.col + offset * (oc.col.and(1))) / 2)
        )

        fun rOffsetToCube(offset: Offset, oc: OffsetCoord) = Hex(
            oc.col - ((oc.row + offset * (oc.row.and(1))) / 2),
            oc.row
        )
    }



}