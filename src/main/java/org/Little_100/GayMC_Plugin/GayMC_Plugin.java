package org.Little_100.GayMC_Plugin;
/*
 * 亻尔女子,我是力涛意摆
 * Hello, I am Little_100.
 * wo shi nan niang
*/

import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import java.util.ResourceBundle;

public final class GayMC_Plugin extends JavaPlugin {
    public static GayMC_Plugin instance;
    private FileConfiguration mainConfig;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        mainConfig = getConfig();
        // Plugin startup logic
        getLogger().info("" +
                "   __ _ _   _   _          _  ___   ___  \n" +
                "  / /(_) |_| |_| | ___    / |/ _ \\ / _ \\ \n" +
                " / / | | __| __| |/ _ \\   | | | | | | | |\n" +
                "/ /__| | |_| |_| |  __/   | | |_| | |_| |\n" +
                "\\____/_|\\__|\\__|_|\\___|___|_|\\___/ \\___/ \n" +
                "                     |_____|             ");
        // 加载配置文件
        FileConfiguration config = this.getConfig();
        ChanceHaveBaby chanceHaveBaby = new ChanceHaveBaby(config);
        Bukkit.getPluginCommand("fuck").setExecutor(new Cmd());
        getCommand("GayMC_Plugin").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new ShiftFuck(this), this);
        instance = this;
    }

    public void reloadAllConfigs() {
        reloadConfig(); // 重新加载主配置文件
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getMainConfig() {
        return mainConfig;
    }
}
