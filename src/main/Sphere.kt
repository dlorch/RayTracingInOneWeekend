package main

import kotlin.math.sqrt

class Sphere(val center: Point3, val radius: Double, val material: Material): Hittable {
    override fun hit(r: Ray, tMin: Double, tMax: Double, rec: HitRecord): Boolean {
        val oc = r.origin - center
        val a = r.direction.lengthSquared()
        val halfB = dot(oc, r.direction)
        val c = oc.lengthSquared() - radius*radius

        val discriminant = halfB * halfB - a*c
        if (discriminant < 0.0) return false
        val sqrtd = sqrt(discriminant)

        val root = (-halfB - sqrtd) / a
        if (root < tMin || tMax < root) {
            val root = (-halfB + sqrtd) / a
            if (root < tMin || tMax < root)
                return false
        }

        rec.t = root
        val p = r.at(rec.t)
        rec.p = Point3(p.x(), p.y(), p.z())
        val outwardNormal = (rec.p - center) / radius
        rec.setFaceNormal(r, outwardNormal)
        rec.material = material

        return true
    }
}