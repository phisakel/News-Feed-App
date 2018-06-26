package com.voltek.newsfeed.common.presentation.base

import android.os.Bundle
import android.support.annotation.CallSuper
import com.arellomobile.mvp.MvpAppCompatActivity
import com.voltek.newsfeed.common.presentation.navigation.Navigator
import com.voltek.newsfeed.common.presentation.navigation.NavigatorHolder

abstract class BaseActivity : MvpAppCompatActivity(), LayoutController, Injectable {

    protected abstract fun getNavigator(): Navigator
    protected abstract fun getNavigationHolder(): NavigatorHolder

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        getNavigationHolder().setNavigator(getNavigator())
    }

    @CallSuper
    override fun onPause() {
        getNavigationHolder().removeNavigator()
        super.onPause()
    }
}