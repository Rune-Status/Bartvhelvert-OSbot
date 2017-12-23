package io.devbot.oldschool.gameserver

import io.devbot.oldschool.gameserver.protocol.ConnectionType
import io.devbot.oldschool.gameserver.protocol.ServerResponse
import java.io.IOException
import java.net.Socket
import java.nio.ByteBuffer

object GameServer {
    /**
     *  world to connect to
     */
    private val WORLD = 18

    /**
     * Default revision to start searching for [revision]
     */
    private val DEFAULT_REVISION = 160

    /**
     * Host address
     */
    val HOST = "oldschool$WORLD.runescape.com"

    /**
     * OSRS server port
     */
    val PORT = 43594

    /**
     * Current OSRS revision
     */
    var revision = DEFAULT_REVISION

    /**
     * Checks if the OSRS revision is the same as [revision] if not it updates it.
     */
    fun checkRevision() {
        try {
            val socket = Socket(HOST, PORT)
            val input = socket.getInputStream()!!
            val output = socket.getOutputStream()!!
            val buffer = ByteBuffer.allocate(5)!!
            buffer.put(ConnectionType.UPDATE_CONNECTION.id.toByte())
            buffer.putInt(revision)
            output.write(buffer.array())
            output.flush()
            val response = input.read()
            input.close()
            output.close()
            when (response) {
                ServerResponse.SUCCESSFUL.id -> return
                ServerResponse.OUT_OF_DATE.id -> {
                    revision++
                    checkRevision()
                }
                else -> throw IOException("Unsupported server response code.")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}