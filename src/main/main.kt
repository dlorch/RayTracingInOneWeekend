package main

import kotlin.math.cos

fun rayColor(r: Ray, world: Hittable, depth: Int): Color {
    var rec = HitRecord()

    // If we've exceeded the ray bounce limit, no more light is gathered.
    if (depth <= 0)
        return Color(0.0, 0.0, 0.0)

    if(world.hit(r, 0.001, infinity, rec)) {
        val scattered = Ray()
        val attenuation = Color()
        if (rec.material.scatter(r, rec, attenuation, scattered)) {
            val result = attenuation * rayColor(scattered, world, depth - 1)
            return Color(result.x(), result.y(), result.z())
        }
        return Color(0.0, 0.0, 0.0)
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
    val samplesPerPixel = 100
    val maxDepth = 50

    // World

    val R = cos(pi/4.0)
    val world = HittableList()

    val materialLeft   = Lambertian(Color(0.0, 0.0, 1.0))
    val materialRight  = Lambertian(Color(1.0, 0.0, 0.0))

    world.add(Sphere(Point3(-R,0.0, -1.0), R, materialLeft))
    world.add(Sphere(Point3( R,0.0, -1.0), R, materialRight))

    // Camera

    val cam = Camera(90.0, aspectRatio)

    // Render

    print("P3\n$imageWidth $imageHeight\n255\n")

    for(j in imageHeight-1 downTo 0) {
        System.err.println("\rScanlines remaining: " + j + " ")
        for(i in 0 until imageWidth) {
            var pixelColor = Color(0.0, 0.0, 0.0)
            for(s in 0 until samplesPerPixel) {
                val u = (i + randomDouble()) / (imageWidth-1)
                val v = (j + randomDouble()) / (imageHeight - 1)
                val r = cam.getRay(u, v)
                val rayColor = rayColor(r, world, maxDepth)
                pixelColor = Color(pixelColor.x() + rayColor.x(), pixelColor.y() + rayColor.y(), pixelColor.z() + rayColor.z())
            }
            print(pixelColor.writeColor(samplesPerPixel))
        }
    }

    System.err.println("\nDone.")
}