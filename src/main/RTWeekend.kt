package main

import kotlin.random.Random
import java.lang.Double.POSITIVE_INFINITY

const val infinity = POSITIVE_INFINITY
const val pi = 3.1415926535897932385

fun degreesToRadians(degrees: Double) = degrees * pi / 180.0

fun randomDouble() = Random.nextDouble()

fun randomDouble(min: Double, max: Double) = min + (max-min) * randomDouble()

fun clamp(x: Double, min: Double, max: Double): Double {
    if (x < min) return min
    if (x > max) return max
    return x
}