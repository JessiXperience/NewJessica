package org.jessixperience.jessica.gui

import java.awt.Color

class Blur(
    private final var blurStart: Int,
    private final var blurEnd: Int,
    private final var colorStart: String,
    private final var colorEnd: String
) {
    private fun hex2Rgb( color: String ): Color {
        return Color.decode( "#" + color.replace( "#", "" ) )
    }

    fun getBackgroundColor( second: Boolean, fadeIn: Boolean ): Int {
        var a: Int = if (second) blurEnd else blurStart
        Color.decode( "#000000" )
        val col: Color = hex2Rgb(if (second) colorEnd else colorStart)
        var r: Int = col.rgb shr 16 and 0xFF
        var b: Int = col.rgb shr 8 and 0xFF
        var g: Int = col.rgb and 0xFF
        val prog: Float = 1f
        a = (a * prog).toInt()
        r = (r * prog).toInt()
        g = (g * prog).toInt()
        b = (b * prog).toInt()
        return a shl 24 or (r shl 16) or (b shl 8) or g
    }

}