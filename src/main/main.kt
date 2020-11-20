package main

import kotlin.math.sqrt

fun hitSphere(center: Point3, radius: Double, r: Ray): Double {
    val oc = r.origin - center
    val a = r.direction.lengthSquared()
    val half_b = dot(oc, r.direction)
    val c = oc.lengthSquared() - radius*radius
    val discriminant = half_b*half_b - a*c
    if (discriminant < 0) {
        return -1.0
    } else {
        return (-half_b - sqrt(discriminant)) / a
    }
}

fun rayColor(r: Ray): Color {
    var t = hitSphere(Point3(0.0, 0.0, -1.0), 0.5, r)
    if (t > 0.0) {
        val N = unitVector(r.at(t) - Vec3(0.0,0.0, -1.0))
        val result = Color(N.x()+1, N.y()+1, N.z()+1) * 0.5
        return Color(result.x(), result.y(), result.z())
    }
    val unitDirection = r.direction.unitVector()
    t = 0.5 * (unitDirection.y() + 1.0)
    val result = Color(1.0, 1.0, 1.0) * (1.0-t) + Color(0.5, 0.7, 1.0) * t
    return Color(result.x(), result.y(), result.z())
}

fun main(args: Array<String>) {

    // Image

    val aspectRatio = 16.0 / 9.0
    val imageWidth = 400
    val imageHeight = (imageWidth / aspectRatio).toInt()

    // Camera

    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    val origin = Point3(0.0, 0.0, 0.0)
    val horizontal = Vec3(viewportWidth, 0.0, 0.0)
    val vertical = Vec3(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vec3(0.0, 0.0, focalLength)

    // Render

    print("P3\n$imageWidth $imageHeight\n255\n")

    for(j in imageHeight-1 downTo 0) {
        System.err.println("\rScanlines remaining: " + j + " ")
        for(i in 0 until imageWidth) {
            val u = i.toDouble() / (imageWidth - 1)
            val v = j.toDouble() / (imageHeight - 1)
            val r = Ray(origin, lowerLeftCorner + horizontal * u + vertical * v - origin)
            val pixelColor = rayColor(r)
            print(pixelColor.writeColor())
        }
    }

    System.err.println("\nDone.")
}