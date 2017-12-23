package io.devbot.bot

import io.devbot.Constants
import io.devbot.bot.listener.CommandListener
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.exceptions.RateLimitedException
import javax.security.auth.login.LoginException

object Bot {
    lateinit var jda: JDA

    fun initialize() {
        val builder = JDABuilder(AccountType.BOT)
        builder.setToken(Constants.TOKEN)
        builder.setAutoReconnect(true)
        builder.setStatus(OnlineStatus.ONLINE)
        builder.addEventListener(CommandListener())
        try {
            jda = builder.buildBlocking()
        } catch (e: LoginException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: RateLimitedException) {
            e.printStackTrace()
        }
    }
}