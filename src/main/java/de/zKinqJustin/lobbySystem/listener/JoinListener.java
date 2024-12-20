package de.zKinqJustin.lobbySystem.listener;

import de.zKinqJustin.lobbySystem.scoreboard.ServerScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinListener implements Listener {

    private final JavaPlugin plugin;

    public JoinListener(JavaPlugin plugin) {

        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(ChatColor.GREEN + "" + ChatColor.UNDERLINE + player.getName() + " hat den Server betreten");

        player.sendMessage(ChatColor.GOLD + "Willkomen und viel vergnügen!");

        player.setGameMode(GameMode.ADVENTURE);

        plugin.getLogger().info("Creating scoreboard for " + player.getName());
        ServerScoreboard scoreboard = new ServerScoreboard(player, plugin);
        scoreboard.createScoreboard();
        plugin.getLogger().info("Scoreboard created for " + player.getName());

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        if (compassMeta != null) {
            compassMeta.setDisplayName(ChatColor.GOLD + "Navigator");
            compass.setItemMeta(compassMeta);
        }
        player.getInventory().setItem(4, compass);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.UNDERLINE + player.getName() + " hat den Server verlassen!");
    }
}