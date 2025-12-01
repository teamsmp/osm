package uk.teamsmp.osmium.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import uk.teamsmp.osmium.Osmium
import uk.teamsmp.osmium.database.Database
import uk.teamsmp.osmium.database.executeQuery
import uk.teamsmp.osmium.database.executeUpdate

class OsmCommand(val plugin: Osmium) : CommandExecutor, TabCompleter {
    val mm = Osmium.mm

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(mm.deserialize("${Osmium.prefix} <red>Invalid usage!</red> Look at <gold>/osm help</gold> for more info."))
            return true
        }

        when (args[0]) {
            "status", "mark" -> {
                val status = Database.getConnection().executeQuery("SELECT mark FROM servers WHERE name = ? LIMIT 1", plugin.osmserver)
                if (status.next()) {
                    val mark = status.getString("mark")
                    sender.sendMessage(mm.deserialize("${Osmium.prefix} <gold>${plugin.osmserver}</gold> is currently marked as <yellow>${mark}</yellow>"))
                }
            }
            "all" -> {
                val status = Database.getConnection().executeQuery("SELECT name, mark, players FROM servers")
                var msg = ""
                msg += "${Osmium.prefix} Status of all servers:"
                while (status.next()) {
                    msg += "<br>  <gold>${status.getString("name")} <gray>- <yellow>${status.getString("mark")}</yellow> <gray>${status.getInt("players")}"
                }
                sender.sendMessage(mm.deserialize(msg))
            }
            "set" -> {
                if (sender.hasPermission("osm.server.set")) {
                    sender.sendMessage(mm.deserialize("${Osmium.prefix} <red>You do not have permission to use this command!</red>"))
                    return true
                }
                if (args.size < 3) {
                    sender.sendMessage(mm.deserialize("${Osmium.prefix} <red>Invalid usage!</red><br>  <yellow>/osm set <server> <mark>"))
                    return true
                }
                val server = args[1]
                val mark = args[2]

                Database.getConnection().executeUpdate("UPDATE servers SET mark = ? WHERE name = ?", mark, server)

                sender.sendMessage(mm.deserialize("${Osmium.prefix} Set mark of <gold>${server}</gold> to <yellow>${mark}</yellow>."))
            }
            else -> {
                sender.sendMessage(mm.deserialize("${Osmium.prefix} <red>Invalid usage!</red> <yellow>${args[0]}</yellow> is not a valid subcommand!"))
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<String>
    ): List<String> {
        if (args.isNotEmpty()) {
            when (args.size) {
                1 -> {
                    return listOf("status", "mark", "all", "set").filter { it.startsWith(args[0]) }
                }
                2 if args[0] == "set" -> {
                    val servers = Database.getConnection().executeQuery("SELECT name FROM servers")
                    val res = mutableListOf<String>()
                    while (servers.next()) {
                        res.add(servers.getString("name"))
                    }
                    return res.filter { it.startsWith(args[1]) }
                }
                3 if args[0] == "set" -> {
                    return listOf("up", "down", "flag", "closed", "stopped").filter { it.startsWith(args[2]) }
                }
            }
        } else {
            return listOf()
        }
        return listOf()
    }
}