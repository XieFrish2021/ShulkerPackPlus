package me.coderfrish.shulkerPack.manager

import com.moandjiezana.toml.Toml
import me.coderfrish.shulkerPack.ShulkerPackException
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ConfigurationManager(private val name: String, private val plugin: JavaPlugin) {
    private val toml: Toml = Toml()

    init {
        if (getConfigFile().exists()) {
            toml.read(getConfigFile())
        } else {
            plugin.server.pluginManager.disablePlugin(plugin)
            throw ShulkerPackException("The configuration file does not exist.")
        }
    }

    private fun getConfigFile(): File = File(plugin.dataFolder, name)

    fun get(key: String): String = this.toml.getString(key)
}
