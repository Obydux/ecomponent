package com.willfp.ecomponent.components

import com.willfp.eco.core.gui.GUIComponent
import com.willfp.eco.core.gui.menu.Menu
import com.willfp.eco.core.gui.onLeftClick
import com.willfp.eco.core.gui.page.PageChanger
import com.willfp.eco.core.gui.slot
import com.willfp.eco.core.gui.slot.Slot
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class PageChangerWithDefault(
    item: ItemStack,
    direction: PageChanger.Direction,
    action: (Player, event: InventoryClickEvent, Slot, Menu) -> Unit
) : GUIComponent {
    private val changer = PageChanger(item, direction)
    private val default = slot(item) {
        onLeftClick(action)
    }

    override fun getRows() = 1
    override fun getColumns() = 1

    override fun getSlotAt(row: Int, column: Int, player: Player, menu: Menu) =
        changer.getSlotAt(1, 1, player, menu) ?: default
}
