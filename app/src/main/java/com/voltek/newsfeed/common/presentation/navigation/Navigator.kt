package com.voltek.newsfeed.common.presentation.navigation

interface Navigator {

    fun executeCommands(vararg commands: Command)
}
