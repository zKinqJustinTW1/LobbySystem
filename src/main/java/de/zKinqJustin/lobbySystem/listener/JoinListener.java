package de.zKinqJustin.lobbySystem.listener;

import de.zKinqJustin.lobbySystem.scoreboard.ServerScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(ChatColor.GREEN + "" + ChatColor.UNDERLINE + player.getName() + " hat den Server betreten");

        player.sendMessage(ChatColor.GOLD + "Willkomen und viel vergnügen!");

        new ServerScoreboard(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.LIGHT_PURPLE+ "" + ChatColor.UNDERLINE + player.getName() + "hat den Server verlassen!");
    }

}
