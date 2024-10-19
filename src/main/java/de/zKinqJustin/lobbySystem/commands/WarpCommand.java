package de.zKinqJustin.lobbySystem.commands;

import de.zKinqJustin.lobbySystem.warp.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;
        if (args.length == 1){
            if (WarpManager.getWarp(args[0]) != null) {
                p.teleport(WarpManager.getWarp(args[0]));
                p.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.GREEN + "Du wurdest zu dem Warp " + args[0] + " teleportiert!");
            }else {
                p.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Dieser Warp existiert nicht!");
            }
        }else if (args.length == 2){
            if (!p.hasPermission("lobbysystem.warp.manage")) {
                p.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "" + ChatColor.UNDERLINE + "Keine Rechte!");
                return false;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (WarpManager.getWarp(args[1]) == null) {
                    WarpManager.createWarp(args[1], p.getLocation());
                    p.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.GREEN + "Du hast den Warp " + args[1] + " erstellt!");
                }else {
                    p.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Dieser Warp existiert nicht!");
                }
            }else if (args[0].equalsIgnoreCase("delete")) {
                if (WarpManager.getWarp(args[1]) != null) {
                    WarpManager.deleteWarp(args[1]);
                    p.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.GREEN + "Du hast den Warp " + args[1] + " gelöscht!");
                }else {
                    p.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Dieser Warp existiert nicht!");
                }

            }
            }
        return false;
    }
}
