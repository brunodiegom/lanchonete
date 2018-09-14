package com.brunodiegom.lanchonete.view.component

/**
 * Selector adapter change listener interface.
 */
interface SelectorAdapterChangeListener {
    /**
     * Notifies selector item value has changed.
     *
     * @param value changed.
     * @param position adapter changed position.
     */
    fun onSelectorAdapterChanged(value: Int, position: Int)
}