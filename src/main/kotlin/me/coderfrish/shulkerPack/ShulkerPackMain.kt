package me.coderfrish.shulkerPack

import me.coderfrish.shulkerPack.listener.MenuEventListener
import me.coderfrish.shulkerPack.logger.ConsoleLogger
import me.coderfrish.shulkerPack.manager.CommandManager
import me.coderfrish.shulkerPack.manager.ConfigurationManager
import me.coderfrish.shulkerPack.manager.LanguageManager
import me.coderfrish.shulkerPack.manager.ListenerManager
import net.kyori.adventure.text.Component
import java.io.File

class ShulkerPackMain: AbstractPlugin() {
    companion object {
        const val BASE_PACKAGE = "me.coderfrish.shulkerPack"
        private lateinit var instance: ShulkerPackMain

        @JvmStatic
        fun getInstance(): ShulkerPackMain = instance
    }

    /* Pre enable. */
    private lateinit var languageManager: LanguageManager
    lateinit var configurationManager: ConfigurationManager

    /* Post enable */
//    private lateinit var listenerManager: ListenerManager
//    private lateinit var commandManager: CommandManager

    override fun init(): Boolean {
        try {
            this.saveResource("config.toml")
            this.saveLanguage()

            configurationManager = ConfigurationManager("config.toml", this)
            languageManager = LanguageManager(configurationManager.get("global.language"), this)

            return true
        } catch (e: Exception) {
            throw ShulkerPackException(e.message!!)
        }
    }

    private fun saveLanguage() {
        this.saveResource("language/en_US.toml")
        this.saveResource("language/zh_CN.toml")
    }

    private fun saveResource(name: String) {
        val file = File(this.dataFolder, name)

        if (!file.exists()) this.saveResource(name, false)
    }

    private val logger = ConsoleLogger("ShulkerPackPlus", this)

    override fun enable() {
        instance = this
//        listenerManager = ListenerManager(this)
//        commandManager = CommandManager(this)
        this.pluginMgr.registerEvents(MenuEventListener(), this)

        logger.info(Component.text(languageManager.get("plugin.enable")))
    }

    override fun disable() {
        logger.info(Component.text(languageManager.get("plugin.disable")))
    }
}
