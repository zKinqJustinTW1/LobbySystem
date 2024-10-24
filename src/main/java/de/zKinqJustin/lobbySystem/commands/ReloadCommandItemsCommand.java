package de.zKinqJustin.lobbySystem.commands;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommandItemsCommand implements CommandExecutor {
    private final main plugin;

    public ReloadCommandItemsCommand(main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("lobbysystem.reloadcommanditems")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        plugin.reloadCommandItems();
        sender.sendMessage(ChatColor.GREEN + "Command items configuration has been reloaded.");
        return true;
    }
}