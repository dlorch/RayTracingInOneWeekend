package main

class Ray(var origin: Point3, var direction: Vec3) {
    fun at(t: Double) = origin + direction * t
}