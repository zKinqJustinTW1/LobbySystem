package de.zKinqJustin.lobbySystem.navigator;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class NavigatorConfig {
    private final JavaPlugin plugin;
    private File configFile;
    private FileConfiguration config;

    public NavigatorConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "navigator_config.yml");
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
        config.set("navigator.title", "§6§lNavigator");
        config.set("navigator.size", 27);

        config.set("navigator.items.spawn.material", "GRASS_BLOCK");
        config.set("navigator.items.spawn.name", "§a§lSpawn");
        config.set("navigator.items.spawn.lore", Arrays.asList("§7Teleportiere dich zum Spawn"));
        config.set("navigator.items.spawn.slot", 11);
        config.set("navigator.items.spawn.command", "spawn");

        config.set("navigator.items.pvp.material", "DIAMOND_SWORD");
        config.set("navigator.items.pvp.name", "§c§lPvP Arena");
        config.set("navigator.items.pvp.lore", Arrays.asList("§7Betrete die PvP Arena"));
        config.set("navigator.items.pvp.slot", 13);
        config.set("navigator.items.pvp.command", "warp pvp");

        config.set("navigator.items.shop.material", "CHEST");
        config.set("navigator.items.shop.name", "§e§lShop");
        config.set("navigator.items.shop.lore", Arrays.asList("§7Öffne den Server Shop"));
        config.set("navigator.items.shop.slot", 15);
        config.set("navigator.items.shop.command", "shop");

        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Konnte die Navigator-Konfiguration nicht speichern: " + e.getMessage());
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Konnte die Navigator-Konfiguration nicht speichern: " + e.getMessage());
        }
    }
}