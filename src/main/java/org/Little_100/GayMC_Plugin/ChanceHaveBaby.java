package org.Little_100.GayMC_Plugin;

import java.util.Random;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChanceHaveBaby {

    private final FileConfiguration config; // 插件的配置文件
    private final Random random = new Random();

    public ChanceHaveBaby(FileConfiguration config) {
        this.config = config;
    }

    public void checkForBaby(Player player) {
        // 获取怀孕的概率
        double chance = config.getDouble("Chance");
        if (random.nextDouble() < chance) {
            // 根据性别的概率决定孩子的性别
            String gender = getBabyGender();
            player.sendMessage("你刚刚有了一个孩子, 是" + gender + "孩！");
        }
    }

    private String getBabyGender() {
        boolean girlEnabled = config.getBoolean("Gril.enabled");
        boolean boyEnabled = config.getBoolean("Boy.enabled");

        double girlChance = config.getDouble("Gril.chance");
        double boyChance = config.getDouble("Boy.chance");

        if (girlEnabled && boyEnabled) {
            // 两者都启用，根据概率决定
            double totalChance = girlChance + boyChance;
            double randomValue = random.nextDouble() * totalChance;
            if (randomValue < girlChance) {
                return "女";
            } else {
                return "男";
            }
        } else if (girlEnabled) {
            return "女";
        } else if (boyEnabled) {
            return "男";
        } else {
            return "未知"; // 如果都未启用
        }
    }
}
