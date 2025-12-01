package uk.teamsmp.osmium.utils

import org.bukkit.entity.Player
import uk.teamsmp.osmium.Osmium

fun sendDiscord(player: Player, onlyOne: Boolean = false) {
    player.sendMessage(Osmium.mm.deserialize(if (onlyOne) "<br><gold><b>Hey there!</gold></b> You seem to be the only person online at the moment. Try joining the Discord server<br><underline>https://discord.gg/sGGqR2wDyg<br>" else "<br><underline>https://discord.gg/sGGqR2wDyg<br>"))
}