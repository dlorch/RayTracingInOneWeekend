package main

fun main(args: Array<String>) {
    val imageWidth = 256
    val imageHeight = 256

    print("P3\n$imageWidth $imageHeight\n255\n")

    for(j in imageHeight-1 downTo 0) {
        for(i in 0 until imageWidth) {
            val r = i.toDouble() / (imageWidth-1)
            val g = j.toDouble() / (imageHeight-1)
            val b = 0.25

            val ir = (255.999 * r).toInt()
            val ig = (255.999 * g).toInt()
            val ib = (255.999 * b).toInt()

            print("$ir $ig $ib\n")
        }
    }
}