package main

class Camera {
    private var origin: Point3
    private var horizontal: Vec3
    private var vertical: Vec3
    private var lowerLeftCorner: Vec3

    init {
        val aspectRatio = 16.0 / 9.0
        val viewportHeight = 2.0
        val viewportWidth = aspectRatio * viewportHeight
        val focalLength = 1.0

        origin = Point3(0.0, 0.0, 0.0)
        horizontal = Vec3(viewportWidth, 0.0, 0.0)
        vertical = Vec3(0.0, viewportHeight, 0.0)
        lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vec3(0.0, 0.0, focalLength)
    }

    fun getRay(u: Double, v: Double): Ray = Ray(origin, lowerLeftCorner + horizontal*u + vertical*v - origin)
}