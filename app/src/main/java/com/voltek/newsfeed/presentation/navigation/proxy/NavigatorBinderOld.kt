package com.voltek.newsfeed.presentation.navigation.proxy

/**
 * Class, that implements navigator, calls setNavigator when ready to perform in-app navigation.
 * (onResume for activity class)
 * When it can no longer provide navigation, calls removeNavigator.
 * (onPause for activity class)
 */
interface NavigatorBinderOld {

    fun setNavigator(navigator: NavigatorOld)

    fun removeNavigator()
}
