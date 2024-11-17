package de.zKinqJustin.lobbySystem.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.List;

public class ServerScoreboard {
    private final Player player;
    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private Scoreboard scoreboard;
    private Objective objective;

    public ServerScoreboard(Player player, JavaPlugin plugin) {
        this.player = player;
        this.plugin = plugin;
        ScoreboardConfig scoreboardConfig = new ScoreboardConfig(plugin);
        scoreboardConfig.loadConfig();
        this.config = scoreboardConfig.getConfig();
    }

    public void createScoreboard() {
        plugin.getLogger().info("Creating scoreboard for " + player.getName());
        ScoreboardManager manager = plugin.getServer().getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        String title = config.getString("scoreboard.title", "  §4ProjektInsane.de  ");
        objective = scoreboard.registerNewObjective("lobby", "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = config.getStringList("scoreboard.lines");
        int score = lines.size();

        for (String line : lines) {
            setScore(replacePlaceholders(line), score);
            score--;
        }

        player.setScoreboard(scoreboard);
        plugin.getLogger().info("Scoreboard set for " + player.getName());
    }

    private void setScore(String text, int score) {
        objective.getScore(text).setScore(score);
    }

    private String replacePlaceholders(String line) {
        if (line.equals("{RANK}")) {
            return player.isOp() ? ChatColor.RED + "Admin" : ChatColor.GRAY + "Spieler";
        } else if (line.equals("{PLAYER_IP}")) {
            return ChatColor.GRAY + player.getAddress().getHostName();
        }
        return line;
    }

    public void update() {
        // Implement update logic if needed
    }
}