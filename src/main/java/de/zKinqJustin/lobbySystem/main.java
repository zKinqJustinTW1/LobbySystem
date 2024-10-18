package de.zKinqJustin.lobbySystem;

import de.zKinqJustin.lobbySystem.listener.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "LobbySystem ist jetzt Einsatzbereit");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new JoinListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
