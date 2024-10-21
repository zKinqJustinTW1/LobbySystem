package de.zKinqJustin.lobbySystem.listener;

import de.zKinqJustin.lobbySystem.build.BuildModeManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildModeListener implements Listener {

    private final BuildModeManager buildModeManager;

    public BuildModeListener(BuildModeManager buildModeManager) {
        this.buildModeManager = buildModeManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!buildModeManager.isInBuildMode(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Du musst im Baumodus sein, um zu bauen.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!buildModeManager.isInBuildMode(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Du musst im Baumodus sein, um zu bauen.");
        }
    }
}