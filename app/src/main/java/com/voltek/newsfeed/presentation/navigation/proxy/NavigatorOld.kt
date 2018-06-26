package com.voltek.newsfeed.presentation.navigation.proxy

/**
 * Implement this interface in class, that handles app navigation (usually activity class)
 */
interface NavigatorOld {

    /**
     * @return true, if command was executed, false, if wasn't.
     */
    fun executeCommand(command: CommandOld): Boolean
}
