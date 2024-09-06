package me.coderfrish.shulkerPack

import org.bukkit.Server
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

abstract class AbstractPlugin: JavaPlugin() {
    open val shulker: Server = this.server
    open val pluginMgr: PluginManager
        get() = this.shulker.pluginManager

    @Deprecated("", ReplaceWith("enable()V"))
    override fun onEnable() {
        if (this.init()) {
            enable()
        } else {
            pluginMgr.disablePlugin(this)
            throw ShulkerPackException("The initialization of the plugin failed.")
        }
    }

    @Deprecated("", ReplaceWith("disable()V"))
    override fun onDisable() {
        this.disable()
    }

    open fun init(): Boolean {
        return true
    }

    abstract fun enable()
    abstract fun disable()
}
