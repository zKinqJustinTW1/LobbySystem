package de.zKinqJustin.lobbySystem.commands;

import de.zKinqJustin.lobbySystem.build.BuildModeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {
    private final BuildModeManager buildModeManager;

    public BuildCommand(BuildModeManager buildModeManager) {
        this.buildModeManager = buildModeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /build [player]");
            return true;
        }

        Player target;
        if (args.length == 1) {
            if (!sender.hasPermission("lobbysystem.build.others")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to toggle build mode for other players.");
                return true;
            }
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
        } else {
            if (!sender.hasPermission("lobbysystem.build")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            target = (Player) sender;
        }

        boolean buildModeEnabled = buildModeManager.toggleBuildMode(target);
        String message = buildModeEnabled ? ChatColor.GREEN + "Build mode enabled." : ChatColor.RED + "Build mode disabled.";
        target.sendMessage(message);

        if (sender != target) {
            sender.sendMessage(ChatColor.YELLOW + "Toggled build mode for " + target.getName() + ". " + message);
        }

        return true;
    }
}