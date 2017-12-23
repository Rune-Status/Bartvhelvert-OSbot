package io.devbot.bot.listener

import io.devbot.Commands
import io.devbot.oldschool.gameserver.GameServer
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class CommandListener: ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        var message = event.message.contentRaw!!
        if(!message.startsWith('!')) {
            return
        } else {
            message = message.substring(1, message.length).toLowerCase()
        }
        val commands = message.split(" ")
        when(commands[0]) {
           in Commands.REVISION -> event.textChannel.sendMessage("The current OSRS revision is ${GameServer.revision}").queue()
        }
    }
}