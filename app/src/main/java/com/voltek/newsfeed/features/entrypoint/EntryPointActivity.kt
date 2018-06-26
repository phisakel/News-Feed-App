package com.voltek.newsfeed.features.entrypoint

import android.os.Bundle
import com.voltek.newsfeed.App
import com.voltek.newsfeed.R
import com.voltek.newsfeed.base.navigation.Navigator
import com.voltek.newsfeed.base.navigation.NavigatorHolder
import com.voltek.newsfeed.base.presentation.BaseActivity
import javax.inject.Inject

class EntryPointActivity : BaseActivity() {

    override fun inject() = App.component.inject(this)
    override fun getLayout() = R.layout.activity_generic
    override fun getNavigator() = navigator
    override fun getNavigationHolder() = navigatorHolder

    private lateinit var navigator: Navigator

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = EntryPointNavigator(this)
    }
}
