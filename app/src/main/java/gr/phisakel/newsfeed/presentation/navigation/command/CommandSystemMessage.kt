package gr.phisakel.newsfeed.presentation.navigation.command

import gr.phisakel.newsfeed.presentation.navigation.proxy.Command

class CommandSystemMessage(
        val message: String = "",
        val error: Exception? = null
) : Command()
