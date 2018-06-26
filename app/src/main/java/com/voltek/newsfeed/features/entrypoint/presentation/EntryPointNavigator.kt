package com.voltek.newsfeed.features.entrypoint.presentation

import android.app.Activity
import com.voltek.newsfeed.base.navigation.Command
import com.voltek.newsfeed.base.navigation.Navigator
import com.voltek.newsfeed.base.navigation.commands.Back
import com.voltek.newsfeed.base.navigation.commands.Go
import com.voltek.newsfeed.base.navigation.commands.Replace

class EntryPointNavigator(private val activity: Activity) : Navigator {

    override fun executeCommands(vararg commands: Command) {
        for (command in commands) {
            applyCommand(command)
        }
    }

    override fun onBackPressed() {
        applyCommand(Back())
    }

    private fun applyCommand(command: Command) = when (command) {
        is Go -> {}
        is Replace -> {}
        else -> throw IllegalArgumentException("Can't execute command of type ${command.javaClass.simpleName}")
    }
}
