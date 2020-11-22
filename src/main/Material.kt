package main

interface Material {
    fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean
}

class Lambertian(val albedo: Color): Material {
    override fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean {
        var scatterDirection = rec.normal + randomUnitVector()

        // Catch degenerate scatter direction
        if (scatterDirection.nearZero())
            scatterDirection = rec.normal

        scattered.copyValuesFrom(Ray(rec.p, scatterDirection))
        attenuation.copyValuesFrom(albedo)
        return true
    }
}

class Metal(val albedo: Color, val f: Double): Material {
    val fuzz = if (f < 1)
        f
    else
        1.0

    override fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean {
        val reflected = reflect(unitVector(rIn.direction), rec.normal)
        scattered.copyValuesFrom(Ray(rec.p, reflected + randomInUnitSphere()*fuzz))
        attenuation.copyValuesFrom(albedo)
        return (dot(scattered.direction, rec.normal) > 0)
    }
}

class Dielectric(val indexOfRefraction: Double): Material {
    override fun scatter(rIn: Ray, rec: HitRecord, attenuation: Color, scattered: Ray): Boolean {
        attenuation.copyValuesFrom(Color(1.0, 1.0, 1.0))
        val refractionRatio = if(rec.frontFace)
            (1.0/indexOfRefraction)
        else
            indexOfRefraction

        val unitDirection = unitVector(rIn.direction)
        val refracted = refract(unitDirection, rec.normal, refractionRatio)

        scattered.copyValuesFrom(Ray(rec.p, refracted))
        return true
    }
}