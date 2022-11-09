package com.willfp.ecomponent

import com.willfp.eco.core.gui.menu.Menu
import org.bukkit.entity.Player
import java.util.UUID

/** Delegates menu state to a fixed variable. */
interface MenuStateVar<T> {
    operator fun get(player: Player): T
    operator fun set(player: Player, value: T)
}

/** Menu state variable with null as default. */
private class NullableMenuStateVar<T : Any>(
    private val menu: Menu,
    private val key: String
) : MenuStateVar<T?> {
    override operator fun get(player: Player): T? =
        menu.getState(player, key)

    override operator fun set(player: Player, value: T?) =
        menu.setState(player, key, value)
}

/** Menu state variable with a fixed default. */
private class NotNullMenuStateVar<T : Any>(
    private val menu: Menu,
    private val key: String,
    private val default: T
) : MenuStateVar<T> {
    override operator fun get(player: Player): T =
        menu.getState(player, key) ?: default

    override operator fun set(player: Player, value: T) =
        menu.setState(player, key, value)
}

/** Create a menu state variable. */
fun <T : Any> menuStateVar(
    menu: Menu,
    key: String
): MenuStateVar<T?> = NullableMenuStateVar(menu, key)

/** Create a menu state variable with a [default] value. */
fun <T : Any> menuStateVar(
    menu: Menu,
    key: String,
    default: T
): MenuStateVar<T> = NotNullMenuStateVar(menu, key, default)

/** Instant delegate state variable. */
fun <T : Any> menuStateVar(
    key: String
) = lazyWithReceiver<Menu, MenuStateVar<T?>> {
    menuStateVar(this, key)
}

/** Instant delegate state variable with a [default]. */
fun <T : Any> menuStateVar(
    key: String,
    default: T
) = lazyWithReceiver<Menu, MenuStateVar<T>> {
    menuStateVar(this, key, default)
}

/** Instant delegate state variable with automatic key generation. */
fun <T : Any> menuStateVar() = lazyWithReceiver<Menu, MenuStateVar<T?>> {
    menuStateVar(this, UUID.randomUUID().toString())
}

/**
 * Instant delegate state variable with a [default] ad automatic key
 * generation.
 */
fun <T : Any> menuStateVar(default: T) = lazyWithReceiver<Menu, MenuStateVar<T>> {
    menuStateVar(this, UUID.randomUUID().toString(), default)
}
