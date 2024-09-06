package me.coderfrish.shulkerPack

@Suppress("unused")
data class ShulkerPackException(override val message: String): RuntimeException(message) {
    constructor(): this("Unknown exception.")
}
