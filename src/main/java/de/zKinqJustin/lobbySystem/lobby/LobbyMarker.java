package de.zKinqJustin.lobbySystem.lobby;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class LobbyMarker {
    private final main plugin;
    private final LobbyMarkerConfig lobbyMarkerConfig;
    private Location pos1;
    private Location pos2;

    public LobbyMarker(main plugin, LobbyMarkerConfig lobbyMarkerConfig) {
        this.plugin = plugin;
        this.lobbyMarkerConfig = lobbyMarkerConfig;
        loadPositions();
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
        savePosition(1, pos1);
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
        savePosition(2, pos2);
    }

    private void savePosition(int posNumber, Location location) {
        FileConfiguration config = lobbyMarkerConfig.getConfig();
        String path = "pos" + posNumber + ".";
        config.set(path + "world", location.getWorld().getName());
        config.set(path + "x", location.getX());
        config.set(path + "y", location.getY());
        config.set(path + "z", location.getZ());
        lobbyMarkerConfig.saveConfig();
    }

    private void loadPositions() {
        FileConfiguration config = lobbyMarkerConfig.getConfig();
        pos1 = loadPosition(config, "pos1");
        pos2 = loadPosition(config, "pos2");
    }

    private Location loadPosition(FileConfiguration config, String path) {
        String worldName = config.getString(path + ".world");
        if (worldName == null || worldName.isEmpty()) {
            return null;
        }
        World world = plugin.getServer().getWorld(worldName);
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        return new Location(world, x, y, z);
    }

    public boolean isComplete() {
        return pos1 != null && pos2 != null;
    }

    public boolean isInside(Location location) {
        if (!isComplete() || location.getWorld() != pos1.getWorld()) {
            return false;
        }

        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return location.getX() >= minX && location.getX() <= maxX &&
                location.getY() >= minY && location.getY() <= maxY &&
                location.getZ() >= minZ && location.getZ() <= maxZ;
    }

    public World getWorld() {
        return pos1 != null ? pos1.getWorld() : (pos2 != null ? pos2.getWorld() : null);
    }

    public String getExitCommand() {
        return lobbyMarkerConfig.getConfig().getString("exit-command", "spawn %player%");
    }
}