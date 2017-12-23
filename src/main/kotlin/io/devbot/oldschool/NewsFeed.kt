package io.devbot.oldschool

import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.devbot.bot.Bot
import net.dv8tion.jda.core.EmbedBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.io.FileNotFoundException
import java.net.URL
import java.time.Instant
import java.time.ZonedDateTime

object NewsFeed {
    private val url = "http://services.runescape.com/m=news/latest_news.rss?oldschool=true"

    private val logger: Logger = LoggerFactory.getLogger(this::class.simpleName)

    private var currentFeed = SyndFeedInput().build(XmlReader(URL(url)))

    fun checkUpdate() {
        if(hasUpdated()) {
            sendUpdate()
        }
    }

    private fun hasUpdated(): Boolean {
        var newFeed: SyndFeed
        try {
            newFeed = SyndFeedInput().build(XmlReader(URL(url)))
        } catch (e: FileNotFoundException) {
            logger.error("Couldn't retrieve RSS feed", e)
            return false
        }

        return if(newFeed.entries[0].link != currentFeed.entries[0].link) {
            currentFeed = newFeed
            true
        } else {
            false
        }
    }

    private fun sendUpdate() {
        val entry = currentFeed.entries[0]
        val message = EmbedBuilder()
        message.setTitle(entry.title, entry.link)
        message.setDescription(entry.description.value)
        message.setColor(Color.orange)
        message.setThumbnail(entry.enclosures[0].url)
        message.setTimestamp(Instant.from(ZonedDateTime.now()))
        val channel = Bot.jda.textChannels.find {it.name.contains("general")} ?: Bot.jda.textChannels[0]!!
        channel.sendMessage(message.build()).queue()
    }
}