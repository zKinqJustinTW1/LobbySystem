package de.zKinqJustin.lobbySystem.navigator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigatorManager {
    private final JavaPlugin plugin;
    private final NavigatorConfig config;
    private final Map<String, String> itemCommands;

    public NavigatorManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = new NavigatorConfig(plugin);
        this.itemCommands = new HashMap<>();
        loadNavigator();
    }

    private void loadNavigator() {
        config.loadConfig();
    }

    public void openNavigator(Player player) {
        String title = config.getConfig().getString("navigator.title", "§6§lNavigator");
        int size = config.getConfig().getInt("navigator.size", 27);
        Inventory inventory = Bukkit.createInventory(null, size, title);

        ConfigurationSection items = config.getConfig().getConfigurationSection("navigator.items");
        if (items != null) {
            for (String key : items.getKeys(false)) {
                ConfigurationSection item = items.getConfigurationSection(key);
                if (item != null) {
                    ItemStack itemStack = createItem(item);
                    int slot = item.getInt("slot", 0);
                    inventory.setItem(slot, itemStack);
                    itemCommands.put(item.getString("name"), item.getString("command", ""));
                }
            }
        }

        player.openInventory(inventory);
    }

    private ItemStack createItem(ConfigurationSection section) {
        Material material = Material.valueOf(section.getString("material", "STONE"));
        String name = section.getString("name", "");
        List<String> lore = section.getStringList("lore");

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void executeCommand(Player player, String itemName) {
        plugin.getLogger().info("Executing command for item: " + itemName);
        String command = itemCommands.get(itemName);
        if (command != null && !command.isEmpty()) {
            plugin.getLogger().info("Command found: " + command);
            player.performCommand(command);
        } else {
            plugin.getLogger().warning("No command found for item: " + itemName);
        }
    }
}