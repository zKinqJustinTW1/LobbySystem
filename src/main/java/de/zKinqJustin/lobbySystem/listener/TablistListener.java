package de.zKinqJustin.lobbySystem.listener;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TablistListener implements Listener {

    private final main plugin;

    public TablistListener(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        plugin.getTablistManager().updateTablist();
    }
}