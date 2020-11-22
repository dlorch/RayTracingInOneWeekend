package main

import kotlin.math.tan

class Camera(
        val lookfrom: Point3,
        val lookat: Point3,
        val vup: Vec3,
        val vfov: Double, // vertical vield-of-view in degrees
        val aspectRatio: Double
) {
    private var origin: Point3
    private var horizontal: Vec3
    private var vertical: Vec3
    private var lowerLeftCorner: Vec3

    init {
        val theta = degreesToRadians(vfov)
        val h = tan(theta/2.0)
        val viewportHeight = 2.0 * h
        val viewportWidth = aspectRatio * viewportHeight

        val w = unitVector(lookfrom - lookat)
        val u = unitVector(cross(vup, w))
        val v = cross(w, u)

        origin = lookfrom
        horizontal = u * viewportWidth
        vertical = v * viewportHeight
        lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - w
    }

    fun getRay(s: Double, t: Double): Ray = Ray(origin, lowerLeftCorner + horizontal*s + vertical*t - origin)
}