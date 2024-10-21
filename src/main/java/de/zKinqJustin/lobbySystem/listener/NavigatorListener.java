package de.zKinqJustin.lobbySystem.listener;

import de.zKinqJustin.lobbySystem.navigator.NavigatorManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class NavigatorListener implements Listener {

    private final JavaPlugin plugin;
    private final NavigatorManager navigatorManager;

    public NavigatorListener(JavaPlugin plugin, NavigatorManager navigatorManager) {
        this.plugin = plugin;
        this.navigatorManager = navigatorManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) &&
                item != null && item.getType() == Material.COMPASS) {
            event.setCancelled(true);
            navigatorManager.openNavigator(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = plugin.getConfig().getString("navigator.title", "§6§lNavigator");
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                Player player = (Player) event.getWhoClicked();
                String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
                plugin.getLogger().info("Navigator item clicked: " + itemName);
                navigatorManager.executeCommand(player, itemName);
                player.closeInventory();
            }
        }
    }
}