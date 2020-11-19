package test

import main.Point3
import main.Ray
import main.Vec3
import org.junit.Test
import kotlin.test.assertEquals

class RayTest {
    @Test
    fun testRayAt() {
        val r = Ray(Point3(0.0, 1.0, 4.0), Vec3(1.1, 1.1, 1.1))
        val expected = Point3(2.2, 3.2, 6.2)
        assertEquals(expected, r.at(2.0))
    }
}