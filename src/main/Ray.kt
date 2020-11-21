package main

class Ray(var origin: Point3 = Point3(0.0, 0.0, 0.0), var direction: Vec3 = Vec3(0.0, 0.0, 0.0)) {
    fun at(t: Double) = origin + direction * t
    fun copyValuesFrom(other: Ray) {
        origin = other.origin
        direction = other.direction
    }
}