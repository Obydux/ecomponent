package com.willfp.ecomponent

import com.willfp.eco.core.gui.GUIComponent
import com.willfp.eco.core.gui.menu.Menu
import com.willfp.eco.core.gui.slot.Slot
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

/** An action when clicking a slot. */
typealias SlotAction = (Player, event: InventoryClickEvent, Slot, Menu) -> Unit

private class OrElseGUIComponent(
    private val delegate: GUIComponent,
    private val slot: Slot?
) : GUIComponent by delegate {
    override fun getSlotAt(row: Int, column: Int, player: Player, menu: Menu): Slot? =
        delegate.getSlotAt(row, column, player, menu) ?: slot
}


/** Show this GUIComponent or default to another slot if null. */
fun GUIComponent.orElse(slot: Slot?): GUIComponent = OrElseGUIComponent(this, slot)
