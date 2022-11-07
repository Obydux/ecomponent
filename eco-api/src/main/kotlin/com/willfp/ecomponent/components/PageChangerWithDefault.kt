package com.willfp.ecomponent.components

import com.willfp.eco.core.gui.onLeftClick
import com.willfp.eco.core.gui.page.PageChanger
import com.willfp.eco.core.gui.slot
import com.willfp.ecomponent.SlotAction
import com.willfp.ecomponent.orElse
import org.bukkit.inventory.ItemStack

/** Page changer with default action to perform if at start / end. */
fun pageChangerWithDefault(
    item: ItemStack,
    direction: PageChanger.Direction,
    action: SlotAction
) = PageChanger(item, direction).orElse(slot(item) {
    onLeftClick(action)
})
