package de.zKinqJustin.lobbySystem.tablist;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class TablistManager {
    private final main plugin;
    private final TablistConfig tablistConfig;
    private final Map<String, Rank> ranks = new HashMap<>();
    private List<String> header = new ArrayList<>();
    private List<String> footer = new ArrayList<>();
    private int headerUpdateInterval;
    private int footerUpdateInterval;
    private boolean sortByRank;

    public TablistManager(main plugin, TablistConfig tablistConfig) {
        this.plugin = plugin;
        this.tablistConfig = tablistConfig;
        loadConfig();
        startUpdateTask();
    }

    private void loadConfig() {
        FileConfiguration config = tablistConfig.getConfig();

        // Load header and footer
        header = config.getStringList("header");
        footer = config.getStringList("footer");
        headerUpdateInterval = config.getInt("header-update-interval", 20);
        footerUpdateInterval = config.getInt("footer-update-interval", 20);

        // Load ranks
        ConfigurationSection ranksSection = config.getConfigurationSection("ranks");
        if (ranksSection != null) {
            for (String rankName : ranksSection.getKeys(false)) {
                ConfigurationSection rankSection = ranksSection.getConfigurationSection(rankName);
                if (rankSection != null) {
                    String prefix = ChatColor.translateAlternateColorCodes('&', rankSection.getString("prefix", ""));
                    String permission = rankSection.getString("permission", "");
                    int priority = rankSection.getInt("priority", 0);
                    ranks.put(rankName, new Rank(rankName, prefix, permission, priority));
                }
            }
        }

        sortByRank = config.getBoolean("sort-by-rank", true);
    }

    private void startUpdateTask() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::updateTablist, 0L, 20L);
    }

    public void updateTablist() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerTablist(player);
        }
    }

    private void updatePlayerTablist(Player player) {
        // Update header and footer
        String headerText = String.join("\n", header);
        String footerText = String.join("\n", footer);
        headerText = ChatColor.translateAlternateColorCodes('&', headerText);
        footerText = ChatColor.translateAlternateColorCodes('&', footerText);
        footerText = footerText.replace("%online_players%", String.valueOf(Bukkit.getOnlinePlayers().size()));
        player.setPlayerListHeaderFooter(headerText, footerText);

        // Update player names with rank prefixes
        Scoreboard scoreboard = player.getScoreboard();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Rank rank = getPlayerRank(p);
            String teamName = rank.getName() + p.getName();
            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }
            team.setPrefix(rank.getPrefix());
            team.addEntry(p.getName());
        }

        // Sort players if enabled
        if (sortByRank) {
            sortPlayers(player);
        }
    }

    private Rank getPlayerRank(Player player) {
        for (Rank rank : ranks.values()) {
            if (player.hasPermission(rank.getPermission())) {
                return rank;
            }
        }
        return ranks.get("default");
    }

    private void sortPlayers(Player player) {
        List<Player> sortedPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        sortedPlayers.sort((p1, p2) -> {
            Rank r1 = getPlayerRank(p1);
            Rank r2 = getPlayerRank(p2);
            return Integer.compare(r2.getPriority(), r1.getPriority());
        });

        int index = 0;
        for (Player p : sortedPlayers) {
            player.getScoreboard().getTeam(getPlayerRank(p).getName() + p.getName()).setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            player.getScoreboard().getTeam(getPlayerRank(p).getName() + p.getName()).addEntry(p.getName());
            player.setPlayerListName(getPlayerRank(p).getPrefix() + p.getName());
            index++;
        }
    }

    private static class Rank {
        private final String name;
        private final String prefix;
        private final String permission;
        private final int priority;

        public Rank(String name, String prefix, String permission, int priority) {
            this.name = name;
            this.prefix = prefix;
            this.permission = permission;
            this.priority = priority;
        }

        public String getName() {
            return name;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getPermission() {
            return permission;
        }

        public int getPriority() {
            return priority;
        }
    }
}