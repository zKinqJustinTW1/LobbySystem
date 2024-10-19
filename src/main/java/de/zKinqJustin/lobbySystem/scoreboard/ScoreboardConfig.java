package de.zKinqJustin.lobbySystem.scoreboard;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ScoreboardConfig {
    private final JavaPlugin plugin;
    private File configFile;
    private FileConfiguration config;

    public ScoreboardConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "scoreboard_config.yml");
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            generateConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void generateConfig() {
        config = new YamlConfiguration();
        config.set("scoreboard.title", "  §4ProjektInsane.de  ");
        config.set("scoreboard.lines", Arrays.asList(
                "§1~~~~~~~~~~",
                "§5Dein Rang",
                "{RANK}",
                "§c",
                "twitch.tv/zKinqJustinTW",
                "§a",
                "§bx.com/zKinqJustin",
                "§e",
                "{PLAYER_IP}",
                "§1~~~~~~~~~~"
        ));

        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Konnte die Scoreboard-Konfiguration nicht speichern: " + e.getMessage());
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}