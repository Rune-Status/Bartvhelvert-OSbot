package io.devbot.oldschool.gameserver.protocol


enum class ServerResponse(val id: Int){
    SUCCESSFUL(0),
    OUT_OF_DATE(6),
}