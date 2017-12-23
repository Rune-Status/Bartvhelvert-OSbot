package io.devbot

import io.devbot.bot.Bot
import io.devbot.oldschool.RoutineService


fun main(args: Array<String>) {
    Bot.initialize()
    RoutineService.startService()
}