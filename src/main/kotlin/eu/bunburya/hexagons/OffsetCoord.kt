package eu.bunburya.hexagons

enum class Offset(val value: Int) {
    EVEN (1),
    ODD (-1);

    operator fun times(other: Int) = this.value * other
    operator fun plus(other: Int) = this.value + other
}

class OffsetCoord (val col: Int, val row: Int) {

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
    }

    fun qOffsetToCube(offset: Offset) = Hex(
        col,
        row - ((col + offset * (col.and(1))) / 2)
    )

    fun rOffsetToCube(offset: Offset) = Hex(
        col - ((row + offset * (row.and(1))) / 2),
        row
    )

}