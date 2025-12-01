package uk.teamsmp.osmium

import org.bukkit.plugin.java.JavaPlugin
import net.kyori.adventure.text.minimessage.MiniMessage
import uk.teamsmp.osmium.commands.*
import uk.teamsmp.osmium.database.Database
import uk.teamsmp.osmium.events.AutodownListener
import uk.teamsmp.osmium.events.PlayerBroadcast

class Osmium : JavaPlugin() {
    var osmserver: String = config.getString("server").toString()
    var players: Int = 0

    companion object {
        val mm = MiniMessage.miniMessage()
        val prefix = "<dark_gray>[<gradient:aqua:dark_purple><b>OSM</b><dark_gray>]<reset>"
    }

    override fun onEnable() {
        logger.info("osmium is enabling...")

        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        logger.info("Registered BungeeCord plugin channel.")

        saveDefaultConfig()
        logger.info("Saved and read config from plugins/osmium/config.yml. Edit it now if you haven't already!")
        osmserver = config.getString("server").toString()

        Database.init(config, this@Osmium)

        server.pluginManager.registerEvents(AutodownListener(this@Osmium), this)
        logger.info("Registered AutodownListener event.")
        server.pluginManager.registerEvents(PlayerBroadcast(this@Osmium), this)
        logger.info("Registered PlayerBroadcast event.")

        getCommand("osm")?.apply {
            val cmd = OsmCommand(this@Osmium)
            setExecutor(cmd)
            tabCompleter = cmd
        }
        logger.info("Registered command /osm.")

        getCommand("hub")?.apply {
            val cmd = HubCommand(this@Osmium)
            setExecutor(cmd)
            tabCompleter = cmd
        }
        logger.info("Registered command /hub")

        getCommand("path")?.apply {
            val cmd = PathCommand(this@Osmium)
            setExecutor(cmd)
            tabCompleter = cmd
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
