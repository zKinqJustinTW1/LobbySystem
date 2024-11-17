package de.zKinqJustin.lobbySystem.commands;

import de.zKinqJustin.lobbySystem.warp.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Verwendung: /warp <name> [spieler] oder /warp <add|delete> <name>");
            return true;
        }

        if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("delete")) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Verwendung: /warp <add|delete> <name>");
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur von Spielern ausgeführt werden.");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("lobbysystem.warp.manage")) {
                player.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "" + ChatColor.UNDERLINE + "Keine Rechte!");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (WarpManager.getWarp(args[1]) == null) {
                    WarpManager.createWarp(args[1], player.getLocation());
                    player.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.GREEN + "Du hast den Warp " + args[1] + " erstellt!");
                } else {
                    player.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Dieser Warp existiert bereits!");
                }
            } else { // delete
                if (WarpManager.getWarp(args[1]) != null) {
                    WarpManager.deleteWarp(args[1]);
                    player.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.GREEN + "Du hast den Warp " + args[1] + " gelöscht!");
                } else {
                    player.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Dieser Warp existiert nicht!");
                }
            }
        } else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur von Spielern ausgeführt werden.");
                return true;
            }
            Player player = (Player) sender;
            teleportPlayer(player, args[0], player);
        } else if (args.length == 2) {
            if (!sender.hasPermission("lobbysystem.warp.others")) {
                sender.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "" + ChatColor.UNDERLINE + "Keine Rechte!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Spieler nicht gefunden!");
                return true;
            }
            teleportPlayer(target, args[0], sender);
        } else {
            sender.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Verwendung: /warp <name> [spieler] oder /warp <add|delete> <name>");
        }
        return true;
    }

    private void teleportPlayer(Player player, String warpName, CommandSender sender) {
        Location warpLocation = WarpManager.getWarp(warpName);
        if (warpLocation != null) {
            player.teleport(warpLocation);
            player.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.GREEN + "Du wurdest zu dem Warp " + warpName + " teleportiert!");
            if (sender != player) {
                sender.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.GREEN + player.getName() + " wurde zu dem Warp " + warpName + " teleportiert!");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_GREEN + "[LobbySystem] " + ChatColor.RED + "Dieser Warp existiert nicht!");
        }
    }
}