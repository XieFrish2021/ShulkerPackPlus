package me.coderfrish.shulkerPack.menu.component

import org.bukkit.event.Event

abstract class AbstractMenu<O: Event, C: Event>: BaseMenu() {
    abstract fun onOpen(event: O): BaseMenu
    abstract fun onClose(event: C)
}
