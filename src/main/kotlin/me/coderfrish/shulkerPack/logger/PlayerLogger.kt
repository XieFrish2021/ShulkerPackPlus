package me.coderfrish.shulkerPack.logger

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

@Deprecated(message = "Temporary deprecation.")
@Suppress("unused")
data class PlayerLogger(private val name: String, private val player: Player) {
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
        player.sendMessage(
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
