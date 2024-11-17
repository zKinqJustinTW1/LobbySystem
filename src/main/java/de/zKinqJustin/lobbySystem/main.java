package de.zKinqJustin.lobbySystem;

import de.zKinqJustin.lobbySystem.build.BuildModeManager;
import de.zKinqJustin.lobbySystem.commands.BuildCommand;
import de.zKinqJustin.lobbySystem.commands.ReloadCommandItemsCommand;
import de.zKinqJustin.lobbySystem.commands.SetPosCommand;
import de.zKinqJustin.lobbySystem.commands.WarpCommand;
import de.zKinqJustin.lobbySystem.items.CommandItemsConfig;
import de.zKinqJustin.lobbySystem.items.CommandItemsManager;
import de.zKinqJustin.lobbySystem.listener.*;
import de.zKinqJustin.lobbySystem.lobby.LobbyMarker;
import de.zKinqJustin.lobbySystem.lobby.LobbyMarkerConfig;
import de.zKinqJustin.lobbySystem.navigator.NavigatorManager;
import de.zKinqJustin.lobbySystem.scoreboard.ScoreboardConfig;
import de.zKinqJustin.lobbySystem.tablist.TablistConfig;
import de.zKinqJustin.lobbySystem.tablist.TablistManager;
import de.zKinqJustin.lobbySystem.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class main extends JavaPlugin {

    private static Config cfg;
    private TablistManager tablistManager;
    private TablistConfig tablistConfig;
    private LobbyMarker lobbyMarker;
    private LobbyMarkerConfig lobbyMarkerConfig;
    private CommandItemsConfig commandItemsConfig;
    private CommandItemsManager commandItemsManager;

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

        tablistConfig = new TablistConfig(this);
        tablistManager = new TablistManager(this, tablistConfig);
        lobbyMarkerConfig = new LobbyMarkerConfig(this);
        lobbyMarker = new LobbyMarker(this, lobbyMarkerConfig);
        commandItemsConfig = new CommandItemsConfig(this);
        commandItemsManager = new CommandItemsManager(this, commandItemsConfig);

        getCommand("warp").setExecutor(new WarpCommand());
        cfg = new Config("warps.yml", getDataFolder());
        getServer().getPluginManager().registerEvents(new NavigatorListener(this, navigatorManager), this);
        getServer().getPluginManager().registerEvents(new BuildModeListener(buildModeManager), this);
        getServer().getPluginManager().registerEvents(new TablistListener(this), this);
        getServer().getPluginManager().registerEvents(new LobbyMarkerListener(this, lobbyMarker), this);
        manager.registerEvents(new CommandItemsListener(this, commandItemsManager), this);

        getCommand("build").setExecutor(new BuildCommand(buildModeManager));
        getCommand("setpos1").setExecutor(new SetPosCommand(this, lobbyMarker, 1));
        getCommand("setpos2").setExecutor(new SetPosCommand(this, lobbyMarker, 2));
        getCommand("reloadcommanditems").setExecutor(new ReloadCommandItemsCommand(this));

    }

    public void onDisable() {
        tablistConfig.saveConfig();
        lobbyMarkerConfig.saveConfig();
        commandItemsConfig.saveConfig();
    }


    public static Config getCfg() {
        return cfg;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }
    public TablistConfig getTablistConfig() {
        return tablistConfig;
    }
    public LobbyMarkerConfig getLobbyMarkerConfig() {
        return lobbyMarkerConfig;
    }
    public LobbyMarker getLobbyMarker() {
        return lobbyMarker;
    }
    public CommandItemsConfig getCommandItemsConfig() {
        return commandItemsConfig;
    }

    public CommandItemsManager getCommandItemsManager() {
        return commandItemsManager;
    }
    public void reloadCommandItems() {
        commandItemsConfig.reloadConfig();
        commandItemsManager.reloadConfig();
    }


}
