package org.Little_100.GayMC_Plugin;
/*
 * 亻尔女子,我是力涛意摆
 * Hello, I am Little_100.
 * wo shi nan niang
*/

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class GayMC_Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("" +
                "   __ _ _   _   _          _  ___   ___  \n" +
                "  / /(_) |_| |_| | ___    / |/ _ \\ / _ \\ \n" +
                " / / | | __| __| |/ _ \\   | | | | | | | |\n" +
                "/ /__| | |_| |_| |  __/   | | |_| | |_| |\n" +
                "\\____/_|\\__|\\__|_|\\___|___|_|\\___/ \\___/ \n" +
                "                     |_____|             ");
        Bukkit.getPluginCommand("fuck").setExecutor(new Cmd());
        getServer().getPluginManager().registerEvents(new ShiftFuck(this), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
