package de.zKinqJustin.lobbySystem.listener;

import de.zKinqJustin.lobbySystem.items.CommandItemsManager;
import de.zKinqJustin.lobbySystem.main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class CommandItemsListener implements Listener {
    private final main plugin;
    private final CommandItemsManager manager;

    public CommandItemsListener(main plugin, CommandItemsManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() || player.hasPermission(manager.getPermission())) {
            manager.giveHotbarItem(player);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            if (item != null && item.getType() == Material.CONDUIT && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String displayName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                if (displayName.equals("Command Menu")) {
                    if (player.isOp() || player.hasPermission(manager.getPermission())) {
                        event.setCancelled(true);
                        manager.openCommandInventory(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', plugin.getCommandItemsConfig().getConfig().getString("inventory.title", "Command Items")))) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if (player.isOp() || player.hasPermission(manager.getPermission())) {
                    manager.executeCommand(player, event.getSlot());
                }
            }
        }
    }
}