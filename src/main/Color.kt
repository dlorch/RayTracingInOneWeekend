package main

class Color(e0: Double = 0.0, e1: Double = 0.0, e2: Double = 0.0): Vec3(e0, e1, e2) {
    fun writeColor() = "" +
            (255.999 * x()).toInt() + " " +
            (255.999 * y()).toInt() + " " +
            (255.999 * z()).toInt() + "\n"
}