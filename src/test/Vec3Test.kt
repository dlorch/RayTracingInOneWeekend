package test

import main.Vec3
import main.dot
import main.cross
import main.unitVector
import org.junit.Test
import kotlin.test.assertEquals

class Vec3Test {
    @Test
    fun testUnaryMinus() {
        val u = Vec3(1.0, 2.0, 3.0)
        val result = -u
        assertEquals(Vec3(-1.0, -2.0, -3.0), result)
    }
    @Test
    fun testAccessElements() {
        val u = Vec3(1.0, 2.0, 3.0)
        assertEquals(1.0, u[0])
        assertEquals(2.0, u[1])
        assertEquals(3.0, u[2])
    }
    @Test
    fun testPlusAssign() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        u += v
        val expected = Vec3(5.0, 7.0, 9.0)
        assertEquals(expected, u)
    }
    @Test
    fun testTimesAssign() {
        val u = Vec3(1.0, 2.0, 3.0)
        u *= 3.0
        val expected = Vec3(3.0, 6.0, 9.0)
        assertEquals(expected, u)
    }
    @Test
    fun testDivAssign() {
        val u = Vec3(2.0, 4.0, 6.0)
        u /= 2.0
        val expected = Vec3(1.0, 2.0, 3.0)
        assertEquals(expected, u)
    }
    @Test
    fun testLength() {
        val u = Vec3(1.0, 2.0, 3.0)
        assertEquals(3.7416573867739413, u.length())
    }
    @Test
    fun testLengthSquared() {
        val u = Vec3(1.0, 2.0, 3.0)
        assertEquals(14.0, u.lengthSquared())
    }
    @Test
    fun testPlus() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        val expected = Vec3(5.0, 7.0, 9.0)
        assertEquals(expected, u + v)
    }
    @Test
    fun testMinus() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        val expected = Vec3(-3.0, -3.0, -3.0)
        assertEquals(expected, u - v)
    }
    @Test
    fun testTimes() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        val expected = Vec3(4.0, 10.0, 18.0)
        assertEquals(expected, u * v)
    }
    @Test
    fun testTimesScalar() {
        val u = Vec3(1.0, 2.0, 3.0)
        val expected = Vec3(2.0, 4.0, 6.0)
        assertEquals(expected, u * 2.0)
    }
    @Test
    fun testDiv() {
        val u = Vec3(2.0, 4.0, 6.0)
        val expected = Vec3(1.0, 2.0, 3.0)
        assertEquals(expected, u / 2.0)
    }
    @Test
    fun testDot() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        assertEquals(32.0, u.dot(v))
    }
    @Test
    fun testDotFun() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        assertEquals(32.0, dot(u, v))
    }
    @Test
    fun testCross() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        val expected = Vec3(-3.0, 6.0, -3.0)
        assertEquals(expected, u.cross(v))
    }
    @Test
    fun testCrossFun() {
        val u = Vec3(1.0, 2.0, 3.0)
        val v = Vec3(4.0, 5.0, 6.0)
        val expected = Vec3(-3.0, 6.0, -3.0)
        assertEquals(expected, cross(u, v))
    }
    @Test
    fun testUnitVector() {
        val u = Vec3(1.0, 2.0, 3.0)
        val expected = Vec3(0.2672612419124244,0.5345224838248488, 0.8017837257372732)
        assertEquals(expected, u.unitVector())
    }
    @Test
    fun testUnitVectorFun() {
        val u = Vec3(1.0, 2.0, 3.0)
        val expected = Vec3(0.2672612419124244,0.5345224838248488, 0.8017837257372732)
        assertEquals(expected, unitVector(u))
    }
}