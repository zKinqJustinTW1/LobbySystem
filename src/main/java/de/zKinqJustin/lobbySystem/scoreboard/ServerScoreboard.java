package de.zKinqJustin.lobbySystem.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ServerScoreboard extends Scoreboardbuilder{
    public ServerScoreboard(Player player) {
        super(player, "  §4ProjektInsane.de  ");
    }

    @Override
    public void  createScoreboard() {
        setScore(ChatColor.DARK_BLUE + "~~~~~~~~~~", 9);
        setScore(ChatColor.DARK_PURPLE + "Dein Rang", 8);

        if(player.isOp()) {
            setScore(ChatColor.RED + "Admin", 7);
        } else {
            setScore(ChatColor.GRAY + "Spieler", 7);
        }

        setScore(ChatColor.RED.toString(), 6);
        setScore("twitch.tv/zKinqJustinTW", 5);
        setScore(ChatColor.GREEN.toString(), 4);
        setScore(ChatColor.AQUA + "x.com/zKinqJustin", 3);
        setScore(ChatColor.YELLOW.toString(), 2);
        setScore(ChatColor.GRAY + player.getAddress().getHostName(), 1);
        setScore(ChatColor.DARK_BLUE + "~~~~~~~~~~", 0);
    }

    @Override
    public void update() {

    }
}
