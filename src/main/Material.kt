package main

import java.lang.Math.pow
import kotlin.math.min
import kotlin.math.sqrt

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
        val cosTheta = min(dot(-unitDirection, rec.normal), 1.0)
        val sinTheta = sqrt(1.0 - cosTheta*cosTheta)

        val cannotRefract = refractionRatio * sinTheta > 1.0
        val direction = if(cannotRefract || reflectance(cosTheta, refractionRatio) > randomDouble())
            reflect(unitDirection, rec.normal)
        else
            refract(unitDirection, rec.normal, refractionRatio)

        scattered.copyValuesFrom(Ray(rec.p, direction))
        return true
    }
}

fun reflectance(cosine: Double, refIdx: Double): Double {
    // Use Schlick's approximation for reflectance.
    var r0 = (1-refIdx) / (1+refIdx)
    r0 = r0*r0
    return r0 + (1-r0)*pow((1 - cosine), 5.0)
}