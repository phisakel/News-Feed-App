package com.voltek.newsfeed.base.navigation

interface Navigator {

    fun executeCommands(vararg commands: Command)

    fun onBackPressed()
}
