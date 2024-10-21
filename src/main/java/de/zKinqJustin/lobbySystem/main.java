package de.zKinqJustin.lobbySystem;

import de.zKinqJustin.lobbySystem.build.BuildModeManager;
import de.zKinqJustin.lobbySystem.commands.BuildCommand;
import de.zKinqJustin.lobbySystem.commands.WarpCommand;
import de.zKinqJustin.lobbySystem.listener.BuildModeListener;
import de.zKinqJustin.lobbySystem.listener.JoinListener;
import de.zKinqJustin.lobbySystem.listener.NavigatorListener;
import de.zKinqJustin.lobbySystem.navigator.NavigatorManager;
import de.zKinqJustin.lobbySystem.scoreboard.ScoreboardConfig;
import de.zKinqJustin.lobbySystem.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    private static Config cfg;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "LobbySystem ist jetzt Einsatzbereit");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "      made by zKinqJustinTW        ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        ScoreboardConfig scoreboardConfig = new ScoreboardConfig(this);
        scoreboardConfig.loadConfig();

        NavigatorManager navigatorManager = new NavigatorManager(this);
        BuildModeManager buildModeManager = new BuildModeManager();

        PluginManager manager = Bukkit.getPluginManager();
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        getCommand("warp").setExecutor(new WarpCommand());
        cfg = new Config("warps.yml", getDataFolder());
        getServer().getPluginManager().registerEvents(new NavigatorListener(this, navigatorManager), this);
        getServer().getPluginManager().registerEvents(new BuildModeListener(buildModeManager), this);

        getCommand("build").setExecutor(new BuildCommand(buildModeManager));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Config getCfg() {
        return cfg;
    }
}
