package com.willfp.ecomponent

import com.willfp.eco.core.gui.GUIComponent
import kotlin.properties.Delegates

abstract class AutofillComponent : GUIComponent {
    protected var maxRows by Delegates.notNull<Int>()
        private set
    protected var maxColumns by Delegates.notNull<Int>()
        private set

    override fun getRows() = maxRows
    override fun getColumns() = maxColumns

    override fun init(maxRows: Int, maxColumns: Int) {
        this.maxRows = maxRows
        this.maxColumns = maxColumns
    }
}
