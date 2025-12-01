package uk.teamsmp.osmium.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerCommandEvent
import org.bukkit.event.server.ServerLoadEvent
import uk.teamsmp.osmium.Osmium
import uk.teamsmp.osmium.database.Database
import uk.teamsmp.osmium.database.executeQuery
import uk.teamsmp.osmium.database.executeUpdate

class AutodownListener(val plugin: Osmium) : Listener {
    @EventHandler
    fun onServerLoad(event: ServerLoadEvent) {
        val query =
            Database.getConnection().executeQuery("SELECT mark FROM servers WHERE name = ? LIMIT 1", plugin.osmserver)
        if (query.next()) {
            val mark = query.getString("mark")
            if (mark == "down" || mark == "stopped" || mark == "autodown") {
                val newMark = (if (mark == "stopped" || mark == "autodown") "up" else "closed")
                Database.getConnection()
                    .executeUpdate("UPDATE servers SET mark = ? WHERE name = ?", newMark, plugin.osmserver)
                plugin.logger.info("Marked server as ${newMark}.")
            }
        }
    }

    @EventHandler
    fun onServerStop(event: ServerCommandEvent) {
        if (event.command == "stop") {
            val query = Database.getConnection()
                .executeQuery("SELECT mark FROM servers WHERE name = ? LIMIT 1", plugin.osmserver)
            if (query.next()) {
                val mark = query.getString("mark")
                if (mark == "up" || mark == "closed") {
                    val newMark = (if (mark == "up") "stopped" else "down")
                    Database.getConnection().executeUpdate("UPDATE servers SET mark = ? WHERE name = ?", newMark, plugin.osmserver)
                    plugin.logger.info("Marked server as ${newMark}.")
                }
            }
        }
    }
}