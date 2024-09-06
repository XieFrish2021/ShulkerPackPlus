package me.coderfrish.shulkerPack.menu

import me.coderfrish.shulkerPack.ShulkerPackException
import me.coderfrish.shulkerPack.data.block.ShulkerBoxData
import me.coderfrish.shulkerPack.menu.component.AbstractMenu
import me.coderfrish.shulkerPack.menu.component.BaseMenu
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.block.ShulkerBox
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import java.util.*

class ShulkerBoxMenu(private val shulkerBox: ShulkerBox): AbstractMenu<PlayerInteractEvent, InventoryCloseEvent>() {
    private val openShulkerBoxes: MutableMap<UUID, ShulkerBoxData> = HashMap<UUID, ShulkerBoxData>()

    override fun onOpen(event: PlayerInteractEvent): BaseMenu {
        val item = event.item!!
        val player = event.player
        val hand = event.hand

        if (hand == EquipmentSlot.OFF_HAND) {
            val inv = inventory()
            player.openInventory(inv)
            openShulkerBoxes[player.uniqueId] = ShulkerBoxData(item, hand)
        }

        return this
    }

    override fun onClose(event: InventoryCloseEvent) {
        val player = event.player
        val playerUUID: UUID = player.uniqueId

        if (player !is Player) return

        if (openShulkerBoxes.containsKey(playerUUID)) {
            val data = openShulkerBoxes[playerUUID]
            closeShulkerBoxContents(player, data!!.item, event.inventory, data.hand)
            openShulkerBoxes.remove(playerUUID)
        }
    }

    override fun inventory(): Inventory {
        val inventory = Bukkit.createInventory(null, InventoryType.SHULKER_BOX, title())
        val contents = shulkerBox.inventory.contents

        inventory.contents = contents
        return inventory
    }

    override fun title(): Component {
        return Component.translatable("block.minecraft.shulker_box")
    }

    private fun closeShulkerBoxContents(player: Player, item: ItemStack, inventory: Inventory, hand: EquipmentSlot) {
        try {
            val meta = item.itemMeta
            if (meta is BlockStateMeta) {
                if (meta.blockState is ShulkerBox) {
                    val shulkerBox = meta.blockState as ShulkerBox
                    shulkerBox.inventory.contents = inventory.contents
                    meta.blockState = shulkerBox
                    item.setItemMeta(meta)

                    if (hand == EquipmentSlot.OFF_HAND) {
                        player.inventory.setItemInOffHand(item)
                    }

                    player.updateInventory()
                }
            }
        } catch (e: Exception) {
            throw ShulkerPackException(e.message!!)
        }
    }
}
