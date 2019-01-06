package gr.phisakel.newsfeed.presentation.navigation.proxy

/**
 * Entry point for navigation commands.
 */
interface Router {

    fun execute(command: Command)
}
