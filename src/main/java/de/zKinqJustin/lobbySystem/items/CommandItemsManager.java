package de.zKinqJustin.lobbySystem.items;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandItemsManager {
    private final main plugin;
    private final CommandItemsConfig config;
    private final Map<Integer, String> slotCommands = new HashMap<>();

    public CommandItemsManager(main plugin, CommandItemsConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void giveHotbarItem(Player player) {
        ItemStack item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Command Menu");
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        player.getInventory().setItem(8, item);
    }

    public void openCommandInventory(Player player) {
        ConfigurationSection invConfig = config.getConfig().getConfigurationSection("inventory");
        if (invConfig == null) {
            player.sendMessage(ChatColor.RED + "Error: Invalid inventory configuration.");
            return;
        }

        String title = ChatColor.translateAlternateColorCodes('&', invConfig.getString("title", "Command Items"));
        int size = invConfig.getInt("size", 27);
        Inventory inventory = Bukkit.createInventory(null, size, title);

        ConfigurationSection itemsSection = config.getConfig().getConfigurationSection("items");
        if (itemsSection != null) {
            for (String key : itemsSection.getKeys(false)) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                if (itemSection != null) {
                    ItemStack item = createItem(itemSection);
                    int slot = itemSection.getInt("slot", 0);
                    inventory.setItem(slot, item);
                    slotCommands.put(slot, itemSection.getString("command", ""));
                }
            }
        }

        player.openInventory(inventory);
    }

    private ItemStack createItem(ConfigurationSection section) {
        String materialName = section.getString("material", "STONE");
        Material material;
        try {
            material = Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid material name in config: " + materialName);
            material = Material.STONE;
        }

        String name = ChatColor.translateAlternateColorCodes('&', section.getString("name", ""));
        List<String> lore = section.getStringList("lore");
        List<String> coloredLore = new ArrayList<>();
        for (String line : lore) {
            coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(coloredLore);
        item.setItemMeta(meta);

        return item;
    }

    public void executeCommand(Player player, int slot) {
        String command = slotCommands.get(slot);
        if (command != null && !command.isEmpty()) {
            command = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    public String getPermission() {
        return config.getConfig().getString("permission", "lobbysystem.commanditems");
    }

    public void reloadConfig() {
        config.reloadConfig();
        slotCommands.clear();
    }
}