package main

class HittableList(val obj: Hittable): Hittable {
    private var objects = ArrayList<Hittable>()
    init {
        objects.add(obj)
    }
    fun clear() {
        objects.clear()
    }
    fun add(obj: Hittable) {
        objects.add(obj)
    }
    override fun hit(r: Ray, tMin: Double, tMax: Double, rec: HitRecord): Boolean {
        var tempRec = HitRecord()
        var hitAnything = false
        var closestSoFar = tMax

        for(obj in objects) {
            if (obj.hit(r, tMin, closestSoFar, tempRec)) {
                hitAnything = true
                closestSoFar = tempRec.t
                rec.copyValuesFrom(tempRec)
            }
        }

        return hitAnything
    }
}