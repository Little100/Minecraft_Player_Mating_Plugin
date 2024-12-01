package org.Little_100.GayMC_Plugin;
/*
 * 亻尔女子,我是力涛意摆
 * Hello, I am Little_100.
 * wo shi nan niang
*/

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class GayMC_Plugin extends JavaPlugin {
    public static GayMC_Plugin instance;
    private FileConfiguration mainConfig;
    private ShopGUI shopGUI;
    @Override
    public void onEnable() {
        this.shopGUI = new ShopGUI(this); // 初始化 shop
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
        Cmd cmdExecutor = new Cmd(this);
        Cmd cmd = new Cmd(this);
        Bukkit.getPluginCommand("fuck").setExecutor(cmdExecutor);
        getCommand("gshop").setExecutor(new ShopCmd(this, shopGUI)); // 传递 ShopGUI 实例
        getCommand("gshopgui").setExecutor(cmdExecutor);
        Bukkit.getPluginCommand("ghelp").setExecutor(cmdExecutor);
        getCommand("GayMC_Plugin").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new ShiftFuck(this), this);
        getServer().getPluginManager().registerEvents(new ShopListener(this.shopGUI), this);
        instance = this;
        ShopGUI shopGUI = new ShopGUI(this);
        getCommand("gshop").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                // 展示商店界面
                // 这里可以实现打开商店的逻辑
                player.sendMessage("打开商店界面...");
            } else {
                sender.sendMessage("此命令只能由玩家执行。");
            }
            return true;
        });
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
