package eu.bunburya.hexagons

/**
 * A class representing offset coordinates of a hexagon, rather than cubic coordinates.  Not dealt with specifically in
 * the implementation guide but taken from the Java code at
 * https://www.redblobgames.com/grids/hexagons/codegen/output/Tests.java
 *
 * See also https://www.redblobgames.com/grids/hexagons/#coordinates
 *
 * @param col  The column in which the hexagon is located.
 * @param row  The row in which the hexagon is located.
 */

data class DoubledCoord(val col: Int, val row: Int) {

    companion object{
        fun qDoubledFromCube(hex: Hex): DoubledCoord {
            val col = hex.q
            val row = 2 * hex.r + hex.q
            return DoubledCoord(col, row)
        }

        fun rDoubledFromCube(hex: Hex): DoubledCoord {
            val col = 2 * hex.q + hex.r
            val row = hex.r
            return DoubledCoord(col, row)
        }
    }

    fun qDoubledToCube(): Hex {
        val q = col
        val r = (row - col) / 2
        val s = -q - r
        return Hex(q, r, s)
    }

    fun rDoubledToCube(): Hex {
        val q = (col - row) / 2
        val r = row
        val s = -q - r
        return Hex(q, r, s)
    }

}