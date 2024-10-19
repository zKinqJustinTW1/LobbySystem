package de.zKinqJustin.lobbySystem.warp;

import de.zKinqJustin.lobbySystem.main;
import org.bukkit.Location;

public class WarpManager {

    public static Location getWarp(String name) {
       return main.getCfg().getConfiguration().getLocation(name);
    }

    public static void createWarp(String name, Location location) {
        main.getCfg().set(name, location);
        main.getCfg().save();
    }

    public static void deleteWarp(String name) {
        main.getCfg().set(name, null);
        main.getCfg().save();
    }
}
