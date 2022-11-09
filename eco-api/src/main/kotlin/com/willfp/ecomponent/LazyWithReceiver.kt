package com.willfp.ecomponent

import java.util.WeakHashMap
import kotlin.reflect.KProperty

/** Stolen from https://stackoverflow.com/a/38084930/11427550. */
class LazyWithReceiver<T, R> internal constructor(val initializer: T.() -> R) {
    private val values = WeakHashMap<T, R>()

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(ref: T, property: KProperty<*>): R = synchronized(values) {
        return values.getOrPut(ref) { ref.initializer() }
    }
}

/** Lazy initializer with a receiver, useful for extension properties. */
fun <T, R> lazyWithReceiver(getter: T.() -> R) = LazyWithReceiver<T, R>() {
    this.getter()
}
