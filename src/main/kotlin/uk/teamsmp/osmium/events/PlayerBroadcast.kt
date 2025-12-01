package uk.teamsmp.osmium.events

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import uk.teamsmp.osmium.Osmium
import uk.teamsmp.osmium.database.Database
import uk.teamsmp.osmium.database.executeUpdate

class PlayerBroadcast(val plugin: Osmium) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val players = Bukkit.getOnlinePlayers().size
        Database.getConnection().executeUpdate("UPDATE servers SET players = ? WHERE name = ?", this.plugin.players, this.plugin.osmserver)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val players = Bukkit.getOnlinePlayers().size - 1
        Database.getConnection().executeUpdate("UPDATE servers SET players = ? WHERE name = ?", this.plugin.players, this.plugin.osmserver)
    }
}