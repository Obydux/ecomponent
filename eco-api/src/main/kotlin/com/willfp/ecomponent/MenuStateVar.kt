package com.willfp.ecomponent

import com.willfp.eco.core.gui.menu.Menu
import org.bukkit.entity.Player

/** Delegate menu state to a fixed variable. */
class MenuStateVar<T : Any>(
    private val menu: Menu,
    private val key: String
) {
    operator fun get(player: Player): T? =
        menu.getState(player, key)

    operator fun set(player: Player, value: T?) =
        menu.setState(player, key, value)
}
