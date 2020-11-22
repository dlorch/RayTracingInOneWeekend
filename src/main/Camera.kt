package main

import kotlin.math.tan

class Camera(
        val lookfrom: Point3,
        val lookat: Point3,
        val vup: Vec3,
        val vfov: Double, // vertical vield-of-view in degrees
        val aspectRatio: Double,
        val aperture: Double,
        val focusDist: Double
) {
    private var origin: Point3
    private var horizontal: Vec3
    private var vertical: Vec3
    private var lowerLeftCorner: Vec3
    private var lensRadius: Double
    private lateinit var u: Vec3
    private lateinit var v: Vec3
    private lateinit var w: Vec3

    init {
        val theta = degreesToRadians(vfov)
        val h = tan(theta/2.0)
        val viewportHeight = 2.0 * h
        val viewportWidth = aspectRatio * viewportHeight

        w = unitVector(lookfrom - lookat)
        u = unitVector(cross(vup, w))
        v = cross(w, u)

        origin = lookfrom
        horizontal = u * viewportWidth * focusDist
        vertical = v * viewportHeight * focusDist
        lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - w*focusDist

        lensRadius = aperture / 2.0
    }

    fun getRay(s: Double, t: Double): Ray {
        val rd = randomInUnitDisk() * lensRadius
        val offset = u * rd.x() + v * rd.y()

        val result = origin + offset
        return Ray(
                Point3(result.x(), result.y(), result.z()),
                lowerLeftCorner + horizontal*s + vertical*t - origin - offset
        )
    }
}