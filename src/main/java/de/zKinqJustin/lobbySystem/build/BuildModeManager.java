package de.zKinqJustin.lobbySystem.build;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BuildModeManager {
    private final Set<UUID> buildModeUsers = new HashSet<>();

    public boolean toggleBuildMode(Player player) {
        if (buildModeUsers.contains(player.getUniqueId())) {
            buildModeUsers.remove(player.getUniqueId());
            player.setGameMode(GameMode.ADVENTURE);
            return false;
        } else {
            buildModeUsers.add(player.getUniqueId());
            player.setGameMode(GameMode.CREATIVE);
            return true;
        }
    }

    public boolean isInBuildMode(Player player) {
        return buildModeUsers.contains(player.getUniqueId());
    }
}