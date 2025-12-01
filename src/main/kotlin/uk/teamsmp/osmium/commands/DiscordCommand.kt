package uk.teamsmp.osmium.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import uk.teamsmp.osmium.Osmium
import uk.teamsmp.osmium.utils.sendDiscord

class DiscordCommand(val plugin: Osmium) : CommandExecutor, TabCompleter {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player: Player = p0 as Player
        sendDiscord(p0)
        return true
    }

    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): List<String?> {
        return listOf()
    }
}