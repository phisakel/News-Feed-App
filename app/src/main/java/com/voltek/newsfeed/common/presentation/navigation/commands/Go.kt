package com.voltek.newsfeed.common.presentation.navigation.commands

import com.voltek.newsfeed.common.presentation.Screen
import com.voltek.newsfeed.common.presentation.navigation.Command
import com.voltek.newsfeed.common.presentation.navigation.ScreenArgs

class Go(val screen: Screen, val args: ScreenArgs?) : Command
