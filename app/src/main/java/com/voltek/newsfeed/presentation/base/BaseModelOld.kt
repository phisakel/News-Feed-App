package com.voltek.newsfeed.presentation.base

/**
 * Base ViewModel class.
 *
 * @param subscriber function, that will be called when model updates
 */
abstract class BaseModelOld(private val subscriber: (BaseModelOld) -> Unit) {

    /**
     * Call this method to render new model state
     */
    fun update() {
        subscriber.invoke(this@BaseModelOld)
    }
}
