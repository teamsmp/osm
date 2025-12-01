package uk.teamsmp.osmium.events

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import uk.teamsmp.osmium.Osmium
import uk.teamsmp.osmium.utils.sendDiscord

class DiscordListener(val plugin: Osmium) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (Bukkit.getOnlinePlayers().size == 1 && plugin.osmserver == "survival") {
            sendDiscord(event.player, true)
        }
    }
}