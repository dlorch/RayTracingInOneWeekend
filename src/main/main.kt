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

fun randomScene(): HittableList {
    val world = HittableList()

    val groundMaterial = Lambertian(Color(0.5, 0.5, 0.5))
    world.add(Sphere(Point3(0.0, -1000.0, 0.0), 1000.0, groundMaterial))

    for(a in -11 until 11) {
        for (b in -11 until 11) {
            val chooseMat = randomDouble()
            val center = Point3(a + 0.9* randomDouble(), 0.2, b + 0.9 * randomDouble())

            if ((center - Point3(4.0, 0.2, 0.0)).length() > 0.9) {
                val sphereMaterial: Material

                if(chooseMat < 0.8) {
                    // diffuse
                    val albedo = randomColor() * randomColor()
                    val albedoColor = Color(albedo.x(), albedo.y(), albedo.z())
                    sphereMaterial = Lambertian(albedoColor)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                } else if (chooseMat < 0.95) {
                    // metal
                    val albedo = randomColor(0.5, 1.0)
                    val fuzz = randomDouble(0.0, 0.5)
                    sphereMaterial = Metal(albedo, fuzz)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                } else {
                    // glass
                    sphereMaterial = Dielectric(1.5)
                    world.add(Sphere(center, 0.2, sphereMaterial))
                }
            }
        }
    }

    val material1 = Dielectric(1.5)
    world.add(Sphere(Point3(0.0, 1.0, 0.0), 1.0, material1))

    val material2 = Lambertian(Color(0.4, 0.2, 0.1))
    world.add(Sphere(Point3(-4.0, 1.0, 0.0), 1.0, material2))

    val material3 = Metal(Color(0.7, 0.6, 0.5), 0.0)
    world.add(Sphere(Point3(4.0, 1.0, 0.0), 1.0, material3))

    return world
}

fun main(args: Array<String>) {

    // Image

    val aspectRatio = 3.0 / 2.0
    val imageWidth = 1200
    val imageHeight = (imageWidth / aspectRatio).toInt()
    val samplesPerPixel = 500
    val maxDepth = 50

    // World

    val world = randomScene()

    // Camera

    val lookfrom = Point3(13.0, 2.0, 3.0)
    val lookat = Point3(0.0, 0.0, 0.0)
    val vup = Vec3(0.0, 1.0, 0.0)
    val distToFocus = 10.0
    val aperture = 0.1

    val cam = Camera(lookfrom, lookat, vup, 20.0, aspectRatio, aperture, distToFocus)

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