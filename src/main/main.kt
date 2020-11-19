package main

fun hitSphere(center: Point3, radius: Double, r: Ray): Boolean {
    val oc = r.origin - center
    val a = dot(r.direction, r.direction)
    val b = 2.0 * dot(oc, r.direction)
    val c = dot(oc, oc) - radius*radius
    val discriminant = b*b - 4*a*c
    return (discriminant > 0)
}

fun rayColor(r: Ray): Color {
    if(hitSphere(Point3(0.0, 0.0, -1.0), 0.5, r)) {
        return Color(1.0, 0.0, 0.0)
    }
    val unitDirection = r.direction.unitVector()
    val t = 0.5 * (unitDirection.y() + 1.0)
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