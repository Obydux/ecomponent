package com.willfp.ecomponent.components

import com.willfp.eco.core.gui.menu.Menu
import com.willfp.eco.core.gui.slot
import com.willfp.eco.core.gui.slot.Slot
import com.willfp.ecomponent.AutofillComponent
import com.willfp.ecomponent.GUIPosition
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.ceil
import kotlin.properties.Delegates

/** The order of the level progression. */
private val progressionOrder = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

/** The state of each level. */
enum class LevelState(
    val key: String
) {
    /** Level is unlocked / complete. */
    UNLOCKED("unlocked"),

    /** Level is currently being worked on. */
    IN_PROGRESS("in-progress"),

    /** Level has yet to be worked on. */
    LOCKED("locked")
}

/** Component to display level progression, for Skills/Jobs/etc. */
@Suppress("MemberVisibilityCanBePrivate")
abstract class LevelComponent : AutofillComponent() {
    private val slots = mutableMapOf<Int, MutableMap<GUIPosition, Slot>>()

    private var isBuilt = false

    abstract val pattern: List<String>

    abstract val maxLevel: Int

    override fun getSlotAt(row: Int, column: Int, player: Player, menu: Menu): Slot? {
        if (!isBuilt) {
            build()
        }

        return slots[menu.getPage(player)]?.get(GUIPosition(row, column))
    }

    var levelsPerPage by Delegates.notNull<Int>()
        private set

    var pages by Delegates.notNull<Int>()
        private set

    private fun build() {
        val progressionSlots = mutableMapOf<Int, GUIPosition>()

        var x = 0
        for (row in pattern) {
            x++
            var y = 0
            for (char in row) {
                y++
                if (char == '0') {
                    continue
                }

                val pos = progressionOrder.indexOf(char)

                if (pos == -1) {
                    continue
                }

                progressionSlots[pos + 1] = GUIPosition(x, y)
            }
        }

        levelsPerPage = progressionSlots.size
        pages = ceil(maxLevel.toDouble() / levelsPerPage).toInt()

        for (page in 1..pages) {
            for ((levelOffset, position) in progressionSlots) {
                val level = ((page - 1) * levelsPerPage) + levelOffset

                if (level > maxLevel) {
                    continue
                }

                val pageSlots = slots[page] ?: mutableMapOf()

                pageSlots[position] = slot { player, menu ->
                    getLevelItem(
                        player,
                        menu,
                        level,
                        getLevelState(
                            player,
                            level
                        )
                    )
                }

                slots[page] = pageSlots
            }
        }
    }

    fun getPageOf(level: Int): Int {
        if (!isBuilt) {
            build()
        }

        return ceil(level.toDouble() / levelsPerPage).toInt()
    }

    /** Get the item to be shown given a specific [level] and [levelState]. */
    abstract fun getLevelItem(player: Player, menu: Menu, level: Int, levelState: LevelState): ItemStack

    /** Get the state given a [player]'s [level]. */
    abstract fun getLevelState(player: Player, level: Int): LevelState
}
