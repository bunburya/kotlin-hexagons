package eu.bunburya.hexagons

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

internal class HexagonTests {

    // Tests mostly based on Java code taken from
    // https://www.redblobgames.com/grids/hexagons/codegen/output/Tests.java

    @Test
    fun hexInit() {
        assertThrows(IllegalArgumentException::class.java) { Hex(1, 1, 1) }
    }

    @Test
    fun hexEquals() {
        assertEquals(Hex(1, -1, 0), Hex(1, -1, 0))
        assertNotEquals(Hex(1, -1, 0), Hex(-1, 1, 0))
    }

    @Test
    fun hexArithmetic() {
        assertEquals(
            Hex(4, -10, 6),
            Hex(1, -3, 2)  + Hex(3, -7, 4)
        )
        assertEquals(
            Hex(-2, 4, -2),
            Hex(1, -3, 2) - Hex(3, -7, 4)
        )
    }

    @Test
    fun hexDirection() {
        assertEquals(Hex(0, -1, 1), Hex(0, 0, 0).direction(2))
    }

    @Test
    fun hexNeighbour() {
        assertEquals(Hex(1, -3, 2), Hex(1, -2, 1).neighbour((2)))
    }

    @Test
    fun hexDiagonal() {
        assertEquals(Hex(-1, -1, 2), Hex(1, -2, 1).diagonalNeighbour((3)))
    }

    @Test
    fun hexDistance() {
        assertEquals(7, Hex(3, -7, 4).distanceTo(Hex(0, 0, 0)))
    }

    @Test
    fun hexRotateRight() {
        assertEquals(Hex(3, -2, -1), Hex(1, -3, 2).rotateRight())
    }

    @Test
    fun hexRotateLeft() {
        assertEquals(Hex(-2, -1, 3), Hex(1, -3, 2).rotateLeft())
    }

    @Test
    fun hexRound() {
        val a = FractionalHex(0.0, 0.0, 0.0)
        val b = FractionalHex(1.0, -1.0, 0.0)
        val c = FractionalHex(0.0, -1.0, 1.0)
        assertEquals(Hex(5, -10, 5), FractionalHex(0.0, 0.0, 0.0).hexLerp(FractionalHex(10.0, -20.0, 10.0), 0.5).hexRound())
        assertEquals(a.hexRound(), a.hexLerp(b, 0.499).hexRound())
        assertEquals(b.hexRound(), a.hexLerp(b, 0.501).hexRound())
        assertEquals(a.hexRound(), FractionalHex(
            a.q * 0.4 + b.q * 0.3 + c.q * 0.3,
            a.r * 0.4 + b.r * 0.3 + c.r * 0.3,
            a.s * 0.4 + b.s * 0.3 + c.s * 0.3
        ).hexRound())
        assertEquals(a.hexRound(), FractionalHex(
            a.q * 0.3 + b.q * 0.3 + c.q * 0.4,
            a.r * 0.3 + b.r * 0.3 + c.r * 0.4,
            a.s * 0.3 + b.s * 0.3 + c.s * 0.4
        ).hexRound())
    }

    @Test
    fun hexLineDraw() {
        assertEquals(
            arrayListOf(
                Hex(0,0,0), Hex(0, -1, 1), Hex(0, -2, 2), Hex(1, -3, 2),
                Hex(1, -4, 3), Hex(1, -5, 4)
            ),
            FractionalHex.hexLineDraw(Hex(0, 0, 0), Hex(1, -5, 4))
        )
    }

    @Test
    fun layout() {
        val h = Hex(3, 4, -7)
        val flat = Layout(LAYOUT_FLAT, Point(10.0, 15.0), Point(35.0, 71.0))
        assertEquals(h, flat.pixelToHex(flat.hexToPixel(h)).hexRound())
        val pointy = Layout(LAYOUT_POINTY, Point(10.0, 15.0), Point(35.0, 71.0))
        assertEquals(h, pointy.pixelToHex(pointy.hexToPixel(h)).hexRound())
    }
}