package main

data class HitRecord(var p: Point3 = Point3(0.0, 0.0, 0.0), var normal: Vec3 = Vec3(0.0, 0.0, 0.0), var material: Material = Lambertian(Color(0.8, 0.8, 0.8)), var t: Double = 0.0, var frontFace: Boolean = false) {
    fun setFaceNormal(r: Ray, outwardNormal: Vec3) {
        frontFace = dot(r.direction, outwardNormal) < 0
        normal = if(frontFace)
            outwardNormal
        else
            -outwardNormal
    }
    fun copyValuesFrom(other: HitRecord) {
        p = other.p
        normal = other.normal
        material = other.material
        t = other.t
        frontFace = other.frontFace
    }
}

interface Hittable {
    fun hit(r: Ray, tMin: Double, tMax: Double, rec: HitRecord): Boolean
}