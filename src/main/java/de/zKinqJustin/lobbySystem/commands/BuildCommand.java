package de.zKinqJustin.lobbySystem.commands;

import de.zKinqJustin.lobbySystem.build.BuildModeManager;
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
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Dieser Befehl kann nur von Spielern ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lobbysystem.build")) {
            player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, diesen Befehl zu verwenden.");
            return true;
        }

        boolean buildModeEnabled = buildModeManager.toggleBuildMode(player);

        if (buildModeEnabled) {
            player.sendMessage(ChatColor.GREEN + "Baumodus aktiviert. Du kannst jetzt bauen.");
        } else {
            player.sendMessage(ChatColor.YELLOW + "Baumodus deaktiviert. Du kannst nicht mehr bauen.");
        }

        return true;
    }
}