package com.voltek.newsfeed.base.navigation.commands

import com.voltek.newsfeed.common.navigation.Screen
import com.voltek.newsfeed.base.navigation.Command
import com.voltek.newsfeed.base.navigation.ScreenArgs

class Go(val screen: Screen, val args: ScreenArgs? = null) : Command