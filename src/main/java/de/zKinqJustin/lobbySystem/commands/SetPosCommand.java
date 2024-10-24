package de.zKinqJustin.lobbySystem.commands;

import de.zKinqJustin.lobbySystem.main;
import de.zKinqJustin.lobbySystem.lobby.LobbyMarker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPosCommand implements CommandExecutor {

    private final main plugin;
    private final LobbyMarker lobbyMarker;
    private final int posNumber;

    public SetPosCommand(main plugin, LobbyMarker lobbyMarker, int posNumber) {
        this.plugin = plugin;
        this.lobbyMarker = lobbyMarker;
        this.posNumber = posNumber;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;

        if (posNumber == 1) {
            lobbyMarker.setPos1(player.getLocation());
            player.sendMessage("Position 1 für den Lobby-Bereich wurde gesetzt.");
        } else if (posNumber == 2) {
            lobbyMarker.setPos2(player.getLocation());
            player.sendMessage("Position 2 für den Lobby-Bereich wurde gesetzt.");
        }

        if (lobbyMarker.isComplete()) {
            player.sendMessage("Der Lobby-Bereich wurde vollständig markiert.");
        }

        return true;
    }
}