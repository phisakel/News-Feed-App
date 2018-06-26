package com.voltek.newsfeed.base.presentation

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.voltek.newsfeed.base.navigation.Navigator
import com.voltek.newsfeed.base.navigation.NavigatorHolder

abstract class BaseActivity : AppCompatActivity(), LayoutController, Injectable {

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

    override fun onBackPressed() {
        getNavigator().onBackPressed()
    }
}
