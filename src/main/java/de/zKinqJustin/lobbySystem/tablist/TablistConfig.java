package de.zKinqJustin.lobbySystem.tablist;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TablistConfig {
    private final main plugin;
    private File configFile;
    private FileConfiguration config;

    public TablistConfig(main plugin) {
        this.plugin = plugin;
        createConfig();
    }

    private void createConfig() {
        configFile = new File(plugin.getDataFolder(), "tablist_config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try (InputStream in = plugin.getResource("tablist_config.yml")) {
                if (in != null) {
                    Files.copy(in, configFile.toPath());
                } else {
                    // If the resource doesn't exist, create an empty file
                    configFile.createNewFile();
                    plugin.getLogger().warning("Default tablist_config.yml not found in plugin resources. Created an empty file.");
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create tablist_config.yml!");
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
        config.set("header", new String[]{"&6Welcome to our server!", "&eEnjoy your stay!"});
        config.set("header-update-interval", 20);
        config.set("footer", new String[]{"&7Visit our website: &bwww.example.com", "&7Players online: &a%online_players%"});
        config.set("footer-update-interval", 20);
        config.set("ranks.owner.prefix", "&4[Owner] ");
        config.set("ranks.owner.permission", "lobbysystem.rank.owner");
        config.set("ranks.owner.priority", 100);
        config.set("ranks.default.prefix", "&7");
        config.set("ranks.default.permission", "lobbysystem.rank.default");
        config.set("ranks.default.priority", 0);
        config.set("sort-by-rank", true);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save tablist_config.yml!");
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}