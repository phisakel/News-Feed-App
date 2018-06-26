package com.voltek.newsfeed.base.navigation

interface Router {

    fun execute(vararg commands: Command)
}
