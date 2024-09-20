package me.coderfrish.shulkerPack.manager

import me.coderfrish.shulkerPack.ShulkerPackMain
import me.coderfrish.shulkerPack.listener.EventListener
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections

@Deprecated(message = "null.")
class ListenerManager(plugin: JavaPlugin) {
    init {
        val classes = Reflections(ShulkerPackMain.BASE_PACKAGE).getTypesAnnotatedWith(EventListener::class.java)

        for (clazz in classes) {
            if (clazz.interfaces.contains(Listener::class.java)) {
                val instance = clazz.getConstructor().newInstance()

                if (instance is Listener) plugin.server.pluginManager.registerEvents(instance, plugin)
            }
        }
    }
}
