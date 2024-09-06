package me.coderfrish.shulkerPack.menu.component

import net.kyori.adventure.text.Component
import org.bukkit.inventory.Inventory

abstract class BaseMenu {
    abstract fun inventory(): Inventory
    abstract fun title(): Component
}
