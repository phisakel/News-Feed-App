package com.voltek.newsfeed.presentation.ui

import com.voltek.newsfeed.presentation.navigation.proxy.CommandOld
import com.voltek.newsfeed.presentation.navigation.proxy.RouterOld
import org.junit.Before

abstract class BasePresenterTest {

    protected val queue = ArrayList<CommandOld>()
    protected val router: RouterOld = object : RouterOld {

        override fun execute(command: CommandOld) {
            queue.add(command)
        }
    }

    @Before
    fun clearQueue() {
        queue.clear()
    }
}
