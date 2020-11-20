package main

data class HitRecord(var p: Point3 = Point3(0.0, 0.0, 0.0), var normal: Vec3 = Vec3(0.0, 0.0, 0.0), var t: Double = 0.0, var frontFace: Boolean = false) {
    fun setFaceNormal(r: Ray, outwardNormal: Vec3) {
        frontFace = dot(r.direction, outwardNormal) < 0
        if(frontFace) {
            normal = outwardNormal
        } else {
            normal = -outwardNormal
        }
    }
    fun copyValuesFrom(other: HitRecord) {
        p = other.p
        normal = other.normal
        t = other.t
        frontFace = other.frontFace
    }
}

interface Hittable {
    fun hit(r: Ray, tMin: Double, tMax: Double, rec: HitRecord): Boolean
}