package de.zKinqJustin.lobbySystem.items;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CommandItemsConfig {
    private final main plugin;
    private File configFile;
    private FileConfiguration config;

    public CommandItemsConfig(main plugin) {
        this.plugin = plugin;
        createConfig();
    }

    private void createConfig() {
        configFile = new File(plugin.getDataFolder(), "command_items.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            config = new YamlConfiguration();
            setDefaults();
            saveConfig();
        } else {
            config = YamlConfiguration.loadConfiguration(configFile);
        }
    }

    private void setDefaults() {
        config.set("permission", "lobbysystem.commanditems");
        config.set("inventory.title", "&6Command Items");
        config.set("inventory.size", 27);

        config.set("items.teleport_spawn.material", "COMPASS");
        config.set("items.teleport_spawn.name", "&aTeleport to Spawn");
        config.set("items.teleport_spawn.lore", new String[]{"&7Click to teleport to spawn"});
        config.set("items.teleport_spawn.slot", 0);
        config.set("items.teleport_spawn.command", "spawn %player%");

        config.set("items.give_diamond.material", "DIAMOND");
        config.set("items.give_diamond.name", "&bGive Diamond");
        config.set("items.give_diamond.lore", new String[]{"&7Click to receive a diamond"});
        config.set("items.give_diamond.slot", 1);
        config.set("items.give_diamond.command", "give %player% diamond 1");

        config.set("items.heal.material", "GOLDEN_APPLE");
        config.set("items.heal.name", "&cHeal");
        config.set("items.heal.lore", new String[]{"&7Click to heal yourself"});
        config.set("items.heal.slot", 2);
        config.set("items.heal.command", "heal %player%");
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save command_items.yml!");
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}