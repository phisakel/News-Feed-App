package gr.phisakel.newsfeed.presentation.navigation

import gr.phisakel.newsfeed.domain.exception.NoConnectionException
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import gr.phisakel.newsfeed.presentation.navigation.command.*
import gr.phisakel.newsfeed.presentation.navigation.proxy.Command
import gr.phisakel.newsfeed.presentation.navigation.proxy.Navigator
import gr.phisakel.newsfeed.presentation.navigation.proxy.NavigatorBinder
import gr.phisakel.newsfeed.presentation.navigation.proxy.Router
import org.junit.Before
import org.junit.Test
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue

class RouterHolderTest {

    private lateinit var routerHolder: RouterHolder

    private lateinit var router: Router
    private lateinit var navigatorBinder: NavigatorBinder

    private val trueNavigator: Navigator = object : Navigator {
        override fun executeCommand(command: Command) = when (command) {
            is CommandNavigatorAttached -> false
            else -> true
        }
    }

    private val falseNavigator: Navigator = object : Navigator {
        override fun executeCommand(command: Command) = false
    }

    @Before
    fun prepare() {
        routerHolder = RouterHolder()
        this.router = routerHolder
        this.navigatorBinder = routerHolder
    }

    @Test
    fun attachDetachNavigator() {
        // Create command that need to be queued
        val commandToQueue = CommandOpenArticlesListScreen()
        commandToQueue.addToQueue = true
        // Execute it without navigator attached
        router.execute(commandToQueue)
        assertEquals(1, routerHolder.commandsQueue.size)
        // Attach navigator, command executes
        navigatorBinder.setNavigator(trueNavigator)
        assertEquals(0, routerHolder.commandsQueue.size)
        assertEquals(0, 0)
        // Detach and execute
        navigatorBinder.removeNavigator()
        router.execute(commandToQueue)
        router.execute(commandToQueue)
        assertEquals(2, routerHolder.commandsQueue.size)
    }

    @Test
    fun queueTest() {
        val command = CommandOpenNewsSourcesScreen()
        // There is no navigator and we don't want add command to queue
        command.addToQueue = false
        router.execute(command)
        assertEquals(0, routerHolder.commandsQueue.size)
        // Now we wand add it to queue
        command.addToQueue = true
        router.execute(command)
        assertEquals(1, routerHolder.commandsQueue.size)
        // Attach navigator, run queue
        navigatorBinder.setNavigator(trueNavigator)
        assertEquals(0, routerHolder.commandsQueue.size)
    }

    @Test
    fun differentNavigators() {
        navigatorBinder.setNavigator(falseNavigator)
        // Execute command, that cannot be performed by this navigator
        val command = CommandSystemMessage()
        command.addToQueue = true
        router.execute(command)
        assertEquals(1, routerHolder.commandsQueue.size)
        // Attach new navigator and execute queue
        navigatorBinder.removeNavigator()
        navigatorBinder.setNavigator(trueNavigator)
        assertEquals(0, routerHolder.commandsQueue.size)
    }

    @Test
    fun allCommandsTest() {
        val command = CommandSystemMessage("Error", NoConnectionException())
        router.execute(command)
        assertTrue(routerHolder.commandsQueue[0] is CommandSystemMessage)

        val realCommand = routerHolder.commandsQueue[0] as CommandSystemMessage
        assertEquals(command.message, realCommand.message)
        assertEquals(command.error, realCommand.error)

        router.execute(CommandBack())
        assertEquals(2, routerHolder.commandsQueue.size)

        val openWebsite = CommandOpenWebsite("url")
        router.execute(openWebsite)
        assertEquals(openWebsite.url, (routerHolder.commandsQueue[2] as CommandOpenWebsite).url)

        val shareArticle = CommandShareArticle("title", "url")
        router.execute(shareArticle)
        assertEquals(4, routerHolder.commandsQueue.size)
        assertEquals(shareArticle.title, (routerHolder.commandsQueue[3] as CommandShareArticle).title)
        assertEquals(shareArticle.url, (routerHolder.commandsQueue[3] as CommandShareArticle).url)

        router.execute(CommandOpenArticleDetailsScreen(ArticleUI()))
        assertTrue((routerHolder.commandsQueue[4] as CommandOpenArticleDetailsScreen).article.isEmpty())
        assertEquals(5, routerHolder.commandsQueue.size)

        navigatorBinder.setNavigator(trueNavigator)
        assertTrue(routerHolder.commandsQueue.isEmpty())
    }
}
