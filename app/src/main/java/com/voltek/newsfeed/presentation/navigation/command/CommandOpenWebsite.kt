package com.voltek.newsfeed.presentation.navigation.command

import com.voltek.newsfeed.presentation.navigation.proxy.CommandOld

/**
 * Open url via implicit intent
 */
class CommandOpenWebsite(val url: String) : CommandOld()
