package com.voltek.newsfeed.presentation.ui.splash.launch

import com.voltek.newsfeed.presentation.navigation.proxy.RouterOld

class LaunchWizard(private val router: RouterOld) {

    private var isOnboardingShown = false
    private var hasEnabledNewsSources = false


    fun start() {

    }

    private fun checkNextStep() {
        when {
            !isOnboardingShown -> {

            }
            !hasEnabledNewsSources -> {

            }
            else -> {

            }
        }
    }
}
