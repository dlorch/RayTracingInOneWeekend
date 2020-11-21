package main

import kotlin.math.sqrt

class Color(var e0: Double = 0.0, var e1: Double = 0.0, var e2: Double = 0.0): Vec3(e0, e1, e2) {
    fun writeColor(samplesPerPixel: Int): String {
        var r = this.x()
        var g = this.y()
        var b = this.z()

        // Divide the color by the number of samples.
        val scale = 1.0 / samplesPerPixel
        r = sqrt(scale * r)
        g = sqrt(scale * g)
        b = sqrt(scale * b)

        // Write the translated [0,255] value of each color component.
        return "" +
               (256 * clamp(r, 0.0, 0.999)).toInt() + " " +
               (256 * clamp(g, 0.0, 0.999)).toInt() + " " +
               (256 * clamp(b, 0.0, 0.999)).toInt() + "\n"
    }
    fun copyValuesFrom(other: Color) {
        e0 = other.e0
        e1 = other.e1
        e2 = other.e2
        e[0] = other.e0
        e[1] = other.e1
        e[2] = other.e2
    }
}