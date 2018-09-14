package com.brunodiegom.lanchonete.view.component

/**
 * Selector change listener interface.
 */
interface SelectorChangeListener {
    /**
     * Notifies when seletor value has changed.
     *
     * @param value changed.
     */
    fun onSelectorChanged(value: Int)
}