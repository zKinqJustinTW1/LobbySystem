package de.zKinqJustin.lobbySystem.listener;

import de.zKinqJustin.lobbySystem.main;
import de.zKinqJustin.lobbySystem.lobby.LobbyMarker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LobbyMarkerListener implements Listener {

    private final main plugin;
    private final LobbyMarker lobbyMarker;

    public LobbyMarkerListener(main plugin, LobbyMarker lobbyMarker) {
        this.plugin = plugin;
        this.lobbyMarker = lobbyMarker;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!lobbyMarker.isComplete()) {
            return;
        }

        Player player = event.getPlayer();
        boolean wasInside = lobbyMarker.isInside(event.getFrom());
        boolean isInside = lobbyMarker.isInside(event.getTo());

        if (wasInside && !isInside) {
            String command = lobbyMarker.getExitCommand();
            command = command.replace("%player%", player.getName());
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
        }
    }
}