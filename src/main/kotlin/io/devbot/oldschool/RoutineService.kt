package io.devbot.oldschool

import io.devbot.oldschool.gameserver.GameServer
import kotlin.concurrent.fixedRateTimer

object RoutineService {
    fun startService() {
        fixedRateTimer("Routine service", true, 0, 60000, {
            NewsFeed.checkUpdate()
            GameServer.checkRevision()
        })
    }
}