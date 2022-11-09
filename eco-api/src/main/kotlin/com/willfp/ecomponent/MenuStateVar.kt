package com.willfp.ecomponent

import com.willfp.eco.core.gui.menu.Menu
import org.bukkit.entity.Player

/** Delegates menu state to a fixed variable. */
interface MenuStateVar<T> {
    operator fun get(player: Player): T
    operator fun set(player: Player, value: T)
}

/** Menu state variable with null as default. */
class NullableMenuStateVar<T : Any>(
    private val menu: Menu,
    private val key: String
) : MenuStateVar<T?> {
    override operator fun get(player: Player): T? =
        menu.getState(player, key)

    override operator fun set(player: Player, value: T?) =
        menu.setState(player, key, value)
}

/** Menu state variable with a fixed default. */
class NotNullMenuStateVar<T : Any>(
    private val menu: Menu,
    private val key: String,
    private val default: T
) : MenuStateVar<T> {
    override operator fun get(player: Player): T =
        menu.getState(player, key) ?: default

    override operator fun set(player: Player, value: T) =
        menu.setState(player, key, value)
}
