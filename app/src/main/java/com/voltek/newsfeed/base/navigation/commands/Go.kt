package com.voltek.newsfeed.base.navigation.commands

import com.voltek.newsfeed.common.navigation.Screen
import com.voltek.newsfeed.base.navigation.Command

class Go(val screen: Screen, val args: Any? = null) : Command
