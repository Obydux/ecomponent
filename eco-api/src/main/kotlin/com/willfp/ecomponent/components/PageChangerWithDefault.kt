package com.willfp.ecomponent.components

import com.willfp.eco.core.gui.GUIComponent
import com.willfp.eco.core.gui.menu.Menu
import com.willfp.eco.core.gui.onShiftLeftClick
import com.willfp.eco.core.gui.page.PageChanger
import com.willfp.eco.core.gui.slot
import com.willfp.eco.core.gui.slot.Slot
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class PageChangerWithDefault(
    item: ItemStack,
    private val direction: PageChanger.Direction,
    private val action: (Player, event: InventoryClickEvent, Slot, Menu) -> Unit
) : GUIComponent {
    private val changer = PageChanger(item, direction)
    private val default = slot(item) {
        onShiftLeftClick(action)
    }

    override fun getRows() = 1
    override fun getColumns() = 1

    override fun getSlotAt(row: Int, column: Int, player: Player, menu: Menu): Slot? {
        val page = menu.getPage(player)
        val maxPage = menu.getMaxPage(player)

        if (page <= 1 && direction == PageChanger.Direction.BACKWARDS) {
            return default
        }

        if (page >= maxPage && direction == PageChanger.Direction.FORWARDS) {
            return default
        }

        return changer.getSlotAt(1, 1, player, menu)
    }
}
