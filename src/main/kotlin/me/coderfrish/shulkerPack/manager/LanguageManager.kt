package me.coderfrish.shulkerPack.manager

import com.moandjiezana.toml.Toml
import me.coderfrish.shulkerPack.ShulkerPackException
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class LanguageManager(private val language: String, private val plugin: JavaPlugin) {
    private val toml: Toml = Toml()

    init {
        if (getLanguageFile().exists()) {
            toml.read(getLanguageFile())
        } else {
            plugin.server.pluginManager.disablePlugin(plugin)
            throw ShulkerPackException("The language file does not exist.")
        }
    }

    private fun getLanguageFile(): File = File(plugin.dataFolder, "language/$language.toml")

    fun get(key: String): String = this.toml.getString(key)
}
