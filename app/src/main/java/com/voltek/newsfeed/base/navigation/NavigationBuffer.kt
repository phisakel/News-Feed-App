package com.voltek.newsfeed.base.navigation

import java.util.*

class NavigationBuffer : NavigatorHolder, Router {

    private var navigator: Navigator? = null
    private val commandsQueue = LinkedList<Array<out Command>>()

    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator

        while (commandsQueue.isNotEmpty()) {
            execute(*commandsQueue.poll())
        }
    }

    override fun removeNavigator() {
        navigator = null
    }

    override fun execute(vararg commands: Command) {
        navigator?.executeCommands(*commands) ?: commandsQueue.add(commands)
    }
}
