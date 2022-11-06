package com.willfp.ecomponent.components

import com.willfp.eco.core.gui.GUIComponent
import com.willfp.eco.core.gui.menu.Menu
import com.willfp.eco.core.gui.slot
import com.willfp.eco.core.gui.slot.Slot
import com.willfp.ecomponent.GUIPosition
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.ceil
import kotlin.properties.Delegates

// Jank? Feature.
private val progressionOrder = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

enum class LevelState {
    UNLOCKED,
    IN_PROGRESS,
    LOCKED
}

@Suppress("MemberVisibilityCanBePrivate")
abstract class LevelComponent(
    pattern: List<String>,
    maxLevel: Int
) : GUIComponent {
    private var _rows by Delegates.notNull<Int>()
    private var _columns by Delegates.notNull<Int>()

    private val slots = mutableMapOf<Int, MutableMap<GUIPosition, Slot>>()

    override fun getRows() = _rows
    override fun getColumns() = _columns

    override fun getSlotAt(row: Int, column: Int, player: Player, menu: Menu): Slot? {
        return slots[menu.getPage(player)]?.get(GUIPosition(row, column))
    }

    override fun init(maxRows: Int, maxColumns: Int) {
        this._rows = maxRows
        this._columns = maxColumns
    }

    val levelsPerPage: Int
    val pages: Int

    init {
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

        println(slots)
    }

    abstract fun getLevelItem(player: Player, menu: Menu, level: Int, levelState: LevelState): ItemStack
    abstract fun getLevelState(player: Player, level: Int): LevelState
}
