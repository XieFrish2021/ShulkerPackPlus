package me.coderfrish.shulkerPack.listener

import me.coderfrish.shulkerPack.ShulkerPackMain
import me.coderfrish.shulkerPack.menu.ShulkerBoxMenu
import org.bukkit.block.ShulkerBox
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.BlockStateMeta

@EventListener
@Suppress("unused")
class MenuEventListener : Listener {
    private lateinit var shulkerBoxMenu: ShulkerBoxMenu
    private val config = ShulkerPackMain.getInstance().configurationManager

    @EventHandler
    fun playerOpenInventoryEvent(event: PlayerInteractEvent) {
        val player = event.player

        val inventory = player.inventory
        val mainHandItem = inventory.itemInOffHand

        if (event.action == Action.RIGHT_CLICK_AIR) {
            val itemMeta = mainHandItem.itemMeta

            if (itemMeta is BlockStateMeta) {
                val blockState = itemMeta.blockState

                if (blockState is ShulkerBox) {
                    shulkerBoxMenu = (
                            ShulkerBoxMenu(blockState).onOpen(event) as ShulkerBoxMenu)
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun inventoryCloseEvent(event: InventoryCloseEvent) {
        try {
            shulkerBoxMenu.onClose(event)
        } catch (_: UninitializedPropertyAccessException) {
        }
    }
}
