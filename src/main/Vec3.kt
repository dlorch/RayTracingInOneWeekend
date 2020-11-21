package main

import kotlin.math.sqrt

open class Vec3(e0: Double = 0.0, e1: Double = 0.0, e2: Double = 0.0) {
    private var e = arrayOf(e0, e1, e2)
    fun x() = e[0]
    fun y() = e[1]
    fun z() = e[2]
    operator fun unaryMinus(): Vec3 = Vec3(-e[0], -e[1], -e[2])
    // a[i] gets translated to a.get(i)
    operator fun get(i: Int): Double = e[i]
    // a += b gets translated to a.plusAssign(b)
    operator fun plusAssign(v: Vec3) {
        e[0] += v[0]
        e[1] += v[1]
        e[2] += v[2]
    }
    // a *= b gets translated to a.timesAssign(b)
    operator fun timesAssign(t: Double) {
        e[0] *= t
        e[1] *= t
        e[2] *= t
    }
    // a /= b gets translated to a.divAssign(b)
    operator fun divAssign(t: Double) {
        this *= 1 / t
    }
    fun length() = sqrt(lengthSquared())
    fun lengthSquared() = e[0]*e[0] + e[1]*e[1] + e[2]*e[2]
    override fun toString() = "" + e[0] + " " + e[1] + " " + e[2]
    override fun equals(other: Any?): Boolean {
        if(other is Vec3) {
            return other.x() == e[0] && other.y() == e[1] && other.z() == e[2]
        }
        return false
    }
    override fun hashCode(): Int {
        return e.hashCode()
    }
    // a + b translates to a.plus(b)
    operator fun plus(v: Vec3) = Vec3(e[0] + v.x(), e[1] + v.y(), e[2] + v.z())
    // a - b translates to a.minus(b)
    operator fun minus(v: Vec3) = Vec3(e[0] - v.x(), e[1] - v.y(), e[2] - v.z())
    // a * b translates to a.times(b)
    operator fun times(v: Vec3) = Vec3(e[0] * v.x(), e[1] * v.y(), e[2] * v.z())
    operator fun times(t: Double) = Vec3(e[0] * t, e[1] * t, e[2] * t)
    // a / b translates to a.div(b)
    operator fun div(t: Double) = this * (1/t)
    fun dot(v: Vec3): Double =
            e[0] * v.x() +
            e[1] * v.y() +
            e[2] * v.z()
    fun cross(v: Vec3): Vec3 = Vec3(
            e[1] * v.z() - e[2] * v.y(),
            e[2] * v.x() - e[0] * v.z(),
            e[0] * v.y() - e[1] * v.x()
    )
    fun unitVector(): Vec3 = this / this.length()
}

fun dot(u: Vec3, v: Vec3): Double =
        u.x() * v.x() +
        u.y() * v.y() +
        u.z() * v.z()

fun cross(u: Vec3, v: Vec3): Vec3 = Vec3(
        u.y() * v.z() - u.z() * v.y(),
        u.z() * v.x() - u.x() * v.z(),
        u.x() * v.y() - u.y() * v.x()
)

fun unitVector(v: Vec3): Vec3 = v / v.length()

fun randomVec3() = Vec3(randomDouble(), randomDouble(), randomDouble())

fun randomVec3(min: Double, max: Double) = Vec3(randomDouble(min, max), randomDouble(min, max), randomDouble(min, max))

fun randomInUnitSphere(): Vec3 {
    while(true) {
        val p = randomVec3(-1.0,1.0)
        if (p.lengthSquared() >= 1.0) continue
        return p
    }
}

fun randomUnitVector() = unitVector(randomInUnitSphere())