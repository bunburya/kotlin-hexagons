package eu.bunburya.hexagons

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

// https://www.redblobgames.com/grids/hexagons/implementation.html#layout

class Orientation (
    val f0: Double,
    val f1: Double,
    val f2: Double,
    val f3: Double,
    val b0: Double,
    val b1: Double,
    val b2: Double,
    val b3: Double,
    val startAngle: Double
)

val LAYOUT_POINTY = Orientation(
    sqrt(3.0), sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0,
    sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0,
    0.5
)

val LAYOUT_FLAT = Orientation(
    3.0 / 2.0, 0.0, sqrt(3.0) / 2.0, sqrt(3.0),
    2.0 / 3.0, 0.0, -1.0 / 3.0, sqrt(3.0) / 3.0,
    0.0
)

/**
 * A class which contains data and helper functions for drawing a hexagon to screen.  Based on the implementation at
 * https://www.redblobgames.com/grids/hexagons/implementation.html#layout
 *
 * @param orientation One of Orientation.LAYOUT_POINTY or Orientation.LAYOUT_FLAT; determines whether the hexagons
 * drawn using this layout are pointy-topped or flat-topped.
 * @param size The size of each hexagon.
 * @param origin The centre of the hexagon at (0, 0, 0) (in cubic coordinates).
 */

class Layout (val orientation: Orientation, val size: Point, val origin: Point) {

    fun hexToPixel(h: Hex): Point {
        // https://www.redblobgames.com/grids/hexagons/implementation.html#hex-to-pixel
        val x = (orientation.f0 * h.q + orientation.f1 * h.r) * size.x
        val y = (orientation.f2 * h.q + orientation.f3 * h.r) * size.y
        return Point(x + origin.x, y + origin.y)
    }

    fun pixelToHex(p: Point): FractionalHex {
        // https://www.redblobgames.com/grids/hexagons/implementation.html#pixel-to-hex
        val point = Point(
            (p.x - origin.x) / size.x,
            (p.y - origin.y) / size.y
        )
        val q = orientation.b0 * point.x + orientation.b1 * point.y
        val r = orientation.b2 * point.x + orientation.b3 * point.y
        return FractionalHex(q, r)
    }

    // https://www.redblobgames.com/grids/hexagons/implementation.html#hex-geometry

    fun hexCornerOffset(corner: Int): Point {
        val angle = 2.0 * PI * (orientation.startAngle + corner) / 6
        return Point(size.x * cos(angle), size.y * sin(angle))
    }

    fun polygonCorners(h: Hex): ArrayList<Point> {
        val corners = arrayListOf<Point>()
        val centre = hexToPixel(h)
        for (i in 0..5) {
            val offset = hexCornerOffset(i)
            corners.add(Point(centre.x + offset.x, centre.y + offset.y))
        }
        return corners
    }

}