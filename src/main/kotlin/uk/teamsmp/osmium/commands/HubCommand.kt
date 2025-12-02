package uk.teamsmp.osmium.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import uk.teamsmp.osmium.Osmium
import uk.teamsmp.osmium.utils.path

class HubCommand(val plugin: Osmium) : CommandExecutor, TabCompleter {
    val mm = Osmium.mm

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender is Player) {
            if (args.isEmpty()) {
                plugin.path(sender, "hub-a")
                return true
            }
            val servers = listOf<String>("hub-a", "hub-b")

            if (args[0].isNotEmpty()) {
                if (servers.contains("hub-" + args[0])) {
                    plugin.path(sender, "hub-" + args[0])
                } else {
                    sender.sendMessage(mm.deserialize("${Osmium.PREFIX} <red>Server <gold>${"hub-" + args[0]}<red> does not exist!"))
                }
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.isNotEmpty() && args.size == 1) {
            return listOf("a", "b")
        }
        return listOf()
    }
}