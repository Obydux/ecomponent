package com.willfp.ecomponent

import com.willfp.eco.core.gui.menu.Menu
import com.willfp.eco.core.gui.menu.MenuBuilder
import com.willfp.eco.core.gui.slot.Slot
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.properties.Delegates

/** Represents an item bound to a captive slot in a menu. */
class CaptiveItem {
    private var isBound = false

    private lateinit var menu: Menu
    private var row by Delegates.notNull<Int>()
    private var column by Delegates.notNull<Int>()

    /** Bind to a slot. */
    internal fun bind(menu: Menu, row: Int, column: Int) {
        require(!isBound) { "Already bound!" }

        this.menu = menu
        this.row = row
        this.column = column
    }

    /** Get the item for a player. */
    operator fun get(player: Player): ItemStack? {
        require(isBound) { "Must be bound to an item!" }
        return menu.getCaptiveItem(player, row, column)
    }
}

/** Set a [slot] at a [row] and [column] and optionally bind it to an item. */
fun MenuBuilder.setSlot(
    row: Int,
    column: Int,
    slot: Slot,
    bindCaptive: CaptiveItem? = null
) {
    this.setSlot(
        row,
        column,
        slot
    )

    this.onBuild {
        bindCaptive?.bind(it, row, column)
    }
}
