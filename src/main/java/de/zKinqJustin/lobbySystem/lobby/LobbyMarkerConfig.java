package de.zKinqJustin.lobbySystem.lobby;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class LobbyMarkerConfig {
    private final main plugin;
    private File configFile;
    private FileConfiguration config;

    public LobbyMarkerConfig(main plugin) {
        this.plugin = plugin;
        createConfig();
    }

    private void createConfig() {
        configFile = new File(plugin.getDataFolder(), "lobby_marker_config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try (InputStream in = plugin.getResource("lobby_marker_config.yml")) {
                if (in != null) {
                    Files.copy(in, configFile.toPath());
                } else {
                    // If the resource doesn't exist, create an empty file
                    configFile.createNewFile();
                    plugin.getLogger().warning("Default lobby_marker_config.yml not found in plugin resources. Created an empty file.");
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create lobby_marker_config.yml!");
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        // Add default values if the file is empty
        if (config.getKeys(false).isEmpty()) {
            addDefaultValues();
        }
    }

    private void addDefaultValues() {
        config.set("exit-command", "spawn %player%");
        config.set("pos1.world", "");
        config.set("pos1.x", 0);
        config.set("pos1.y", 0);
        config.set("pos1.z", 0);
        config.set("pos2.world", "");
        config.set("pos2.x", 0);
        config.set("pos2.y", 0);
        config.set("pos2.z", 0);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save lobby_marker_config.yml!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}