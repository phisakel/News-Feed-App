package com.voltek.newsfeed.common.presentation.navigation

import java.util.*

class NavigationBuffer : NavigatorHolder {

    private var navigator: Navigator? = null
    private val commandsQueue = LinkedList<Array<out Command>>()

    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator


        while (commandsQueue.isNotEmpty()) {
            executeCommands(*commandsQueue.poll())
        }
    }

    override fun removeNavigator() {
        navigator = null
    }

    fun executeCommands(vararg commands: Command) {
        navigator?.executeCommands(*commands) ?: commandsQueue.add(commands)
    }
}
