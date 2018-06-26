package com.voltek.newsfeed.common.presentation.navigation

abstract class BaseRouter {

    private val navigationBuffer = NavigationBuffer()

    fun getNavigatorHolder(): NavigatorHolder {
        return navigationBuffer
    }

    protected fun executeCommands(vararg command: Command) {
        navigationBuffer.executeCommands(*command)
    }
}
