package ua.syt0r

fun Float.clamp(min: Float, max: Float): Float {
    return when {
        this > max -> max
        this < min -> min
        else -> this
    }
}