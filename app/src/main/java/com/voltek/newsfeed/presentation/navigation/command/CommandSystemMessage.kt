package com.voltek.newsfeed.presentation.navigation.command

import com.voltek.newsfeed.presentation.navigation.proxy.CommandOld

class CommandSystemMessage(
        val message: String = "",
        val error: Exception? = null
) : CommandOld()
