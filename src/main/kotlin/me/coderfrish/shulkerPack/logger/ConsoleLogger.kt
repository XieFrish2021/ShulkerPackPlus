package me.coderfrish.shulkerPack.logger

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
data class ConsoleLogger(private val name: String, val plugin: JavaPlugin) {
    private val consoleSender = plugin.server.consoleSender

    fun info(component: Component) {
        logger(component, NamedTextColor.GREEN)
    }

    fun warn(component: Component) {
        logger(component, NamedTextColor.YELLOW)
    }

    fun error(component: Component) {
        logger(component, NamedTextColor.RED)
    }

    private fun logger(component: Component, color: NamedTextColor) {
        consoleSender.sendMessage(
            colorText(NamedTextColor.BLACK, "[")
                .append(colorText(color, name))
                .append(colorText(NamedTextColor.BLACK, "]"))
                .append(colorText(NamedTextColor.BLACK, " - "))
                .append(component)
        )
    }

    private fun colorText(color: NamedTextColor, msg: String): Component {
        return Component.text(msg).color(color)
    }
}
