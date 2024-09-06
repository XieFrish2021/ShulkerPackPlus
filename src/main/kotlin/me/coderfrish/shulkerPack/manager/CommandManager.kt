package me.coderfrish.shulkerPack.manager

import me.coderfrish.shulkerPack.ShulkerPackException
import me.coderfrish.shulkerPack.ShulkerPackMain
import me.coderfrish.shulkerPack.command.CommandExecute
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections

class CommandManager(plugin: JavaPlugin) {
    init {
        val classes = Reflections(ShulkerPackMain.BASE_PACKAGE).getTypesAnnotatedWith(CommandExecute::class.java)

        for (clazz in classes) {
            if (clazz.interfaces.contains(CommandExecutor::class.java)) {
                val instance = clazz.getConstructor().newInstance()

                if (instance is CommandExecutor) {
                    val name = clazz.getAnnotation(CommandExecute::class.java).name

                    if (name.isEmpty() || name.isBlank()) throw ShulkerPackException("The command name is empty.")
                    plugin.getCommand(name)!!.setExecutor(instance)
                }
            }
        }
    }
}
