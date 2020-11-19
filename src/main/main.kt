package main

fun main(args: Array<String>) {
    // Image

    val imageWidth = 256
    val imageHeight = 256

    // Render

    print("P3\n$imageWidth $imageHeight\n255\n")

    for(j in imageHeight-1 downTo 0) {
        for(i in 0 until imageWidth) {
            val pixelColor = Color(
                    i.toDouble() / (imageWidth-1),
                    j.toDouble() / (imageHeight-1),
                    0.25
            )
            print(pixelColor.writeColor())
        }
    }
}