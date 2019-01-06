package gr.phisakel.newsfeed.presentation.navigation.command

import gr.phisakel.newsfeed.presentation.navigation.proxy.Command

/**
 * Open url via implicit intent
 */
class CommandOpenWebsite(val url: String) : Command()
