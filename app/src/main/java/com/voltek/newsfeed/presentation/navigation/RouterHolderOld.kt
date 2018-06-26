package com.voltek.newsfeed.presentation.navigation

import com.voltek.newsfeed.presentation.navigation.command.CommandNavigatorAttached
import com.voltek.newsfeed.presentation.navigation.proxy.*
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Manages app navigation, lifecycle tied to app process. Can hold only one navigator at a time.
 * When RouterOld receives command, it will be executed by current navigator or added to queue,
 * if there is no navigator attached or current navigator can not execute it.
 * When new navigator get attached, holder will try to execute queued commands.
 */
class RouterHolderOld : RouterOld, NavigatorBinderOld {

    private val commandsFeed: PublishSubject<CommandOld> = PublishSubject.create()

    private var navigator: NavigatorOld? = null

    val commandsQueue: CopyOnWriteArrayList<CommandOld> = CopyOnWriteArrayList()

    init {
        subscribeToCommandsFeed()
    }

    private fun subscribeToCommandsFeed() {
        commandsFeed
                .filter { !runCommand(it) } // If command executed
                .filter { !runQueue(it) } // If new navigator attached
                .subscribe { addToQueue(it) }
    }

    // RouterOld
    override fun execute(command: CommandOld) {
        commandsFeed.onNext(command)
    }

    // NavigatorBinderOld
    override fun setNavigator(navigator: NavigatorOld) {
        this.navigator = navigator
        commandsFeed.onNext(CommandNavigatorAttached())
    }

    override fun removeNavigator() {
        this.navigator = null
    }

    // Private logic

    /**
     * @return If navigator = null returns false, if command executed - returns true,
     *         if command cannot be executed - false.
     */
    private fun runCommand(command: CommandOld): Boolean = navigator?.executeCommand(command) ?: false

    /**
     * @return true, if new navigator has been attached (tries to execute all commands in queue)
     *         else returns false. (Without queue execution)
     */
    private fun runQueue(command: CommandOld) =
            if (command is CommandNavigatorAttached) {
                commandsQueue
                        .filter { runCommand(it) }
                        .forEach { removeFromQueue(it) }

                true
            } else {
                false
            }

    /**
     * @return true, if command was added to queue, else false.
     */
    private fun addToQueue(command: CommandOld) =
            if (command.addToQueue) {
                if (commandsQueue.isNotEmpty())
                    command.id = commandsQueue[commandsQueue.lastIndex].id + 1
                commandsQueue.add(command)
            } else {
                false
            }

    /**
     * @return true, if command removed successfully, else false
     */
    private fun removeFromQueue(command: CommandOld) = commandsQueue.remove(command)
}
