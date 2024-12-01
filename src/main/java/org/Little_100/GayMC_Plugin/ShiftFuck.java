package org.Little_100.GayMC_Plugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashSet;
import java.util.Set;

public class ShiftFuck implements Listener {
    private static final int RADIUS = 3; //3x3x3的范围
    private final Plugin plugin;
    private final ChanceHaveBaby chanceHaveBaby;
    private int sneaktimes = 0;    //定义搜索范围为3格
    public ShiftFuck(Plugin plugin) {
        this.plugin = plugin;
        this.chanceHaveBaby = new ChanceHaveBaby(plugin.getConfig());
    }
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (event.isSneaking()) {
            // 玩家开始潜行
            Location location = player.getLocation();
            World world = location.getWorld();
            boolean hasOtherPlayer = false;
            boolean hasBed = false;


            // 检测玩家附近的其他玩家
            for (Player nearbyPlayer : world.getPlayers()) {
                if (nearbyPlayer != player && nearbyPlayer.getLocation().distance(location) <= (RADIUS + 2)) {
                    hasOtherPlayer = true;
                    break;
                }
            }
            Set<Material> bedMaterials = new HashSet<>();
            bedMaterials.add(Material.WHITE_BED);
            bedMaterials.add(Material.ORANGE_BED);
            bedMaterials.add(Material.MAGENTA_BED);
            bedMaterials.add(Material.LIGHT_BLUE_BED);
            bedMaterials.add(Material.YELLOW_BED);
            bedMaterials.add(Material.LIME_BED);
            bedMaterials.add(Material.PINK_BED);
            bedMaterials.add(Material.GRAY_BED);
            bedMaterials.add(Material.LIGHT_GRAY_BED);
            bedMaterials.add(Material.CYAN_BED);
            bedMaterials.add(Material.PURPLE_BED);
            bedMaterials.add(Material.BLUE_BED);
            bedMaterials.add(Material.BROWN_BED);
            bedMaterials.add(Material.GREEN_BED);
            bedMaterials.add(Material.RED_BED);
            bedMaterials.add(Material.BLACK_BED);
            for (int x = -RADIUS; x <= RADIUS; x++) {
                for (int y = -RADIUS; y <= RADIUS; y++) {
                    for (int z = -RADIUS; z <= RADIUS; z++) {
                        Block block = location.getWorld().getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                        if (bedMaterials.contains(block.getType())) {
                            hasBed = true;
                            break;
                        }
                    }
                    if (hasBed) break;
                }
                if (hasBed) break;
            }
            if (hasOtherPlayer && hasBed) {
                // 玩家潜行时，如果附近有其他玩家和床，则给玩家恢复生命
                giveRegenerationEffect(player);
                // 给玩家恢复生命
                giveHeartParticleEffect(player);
                // 给玩家播放音效
                playSoundAtPlayer(player, Sound.ENCHANT_THORNS_HIT, 1.0f, 1.0f);
                // 给满足条件的玩家发送信息
                sneaktimes++;
                if (sneaktimes == 100){
                    player.sendMessage("Oh yeah! That's Good!!!!!!!!!!!");
                    //为玩家胸部往下的地方添加效果(item_snowball)
                    spawnElectricParticleBelowChest(player);
                    spawnMilkBukkitRunnable(player);
                    sneaktimes = 0;
                    //引用ChanceHaveBaby类 实现玩家怀孕的效果
                    chanceHaveBaby.checkForBaby(player);
                }
                sneaktimes = 0;
            }
        }
    }

    public void spawnMilkBukkitRunnable(Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();
        ItemStack milkBucket = new ItemStack(Material.MILK_BUCKET);
        ItemMeta meta = milkBucket.getItemMeta();
        meta.setDisplayName("U Know what's this");
        milkBucket.setItemMeta(meta);

        //在玩家面前生成
        double angle = Math.toRadians(player.getLocation().getYaw());
        double x = player.getLocation().getX() + 2 * Math.cos(angle);
        double z = player.getLocation().getZ() + 2 * Math.sin(angle);
        Location targetLocation = new Location(world, x, location.getY(), z);
        //生成
        Item item = world.dropItemNaturally(targetLocation, milkBucket);

        item.setPickupDelay(20 * 60 * 60 * 24 * 365);

        new BukkitRunnable() {
            @Override
            public void run() {
                // 5秒后移除牛奶桶
                if (item.isValid()) {
                    item.remove();
                }
            }
        }.runTaskLater(GayMC_Plugin.instance, 20 * 5); // 5秒后执行，20 ticks/s
    }
    public void spawnElectricParticleBelowChest(Player player) {
        Location location = player.getLocation(); // 获取玩家当前位置
        World world = location.getWorld(); // 获取玩家所在世界

        // 调整位置到玩家胸部下方
        double offset = 0.4; // 可以根据需要调整偏移量
        Location belowChestLocation = location.clone().subtract(0, offset, 0);
        int duration = 5000;
        int period = 50;
        // 生成粒子效果
        world.spawnParticle(Particle.ELECTRIC_SPARK, belowChestLocation, 1);
        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                if (ticks * 50 < duration) {
                    world.spawnParticle(Particle.ELECTRIC_SPARK, belowChestLocation, 1);
                    ticks++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }
    private void giveRegenerationEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
    }

    private void giveHeartParticleEffect(Player player) {
        Location location = player.getLocation(); // 获取玩家的位置
        World world = location.getWorld(); // 获取玩家所在的世界

        // 在玩家位置生成爱心粒子效果
        // 以下数字代表粒子数量，以及它们在X, Y, Z轴上的偏移量
        world.spawnParticle(Particle.HEART, location, 5, 0.1f, 0.1f, 0.1f);
    }

    private void playSoundAtPlayer(Player player, Sound sound, float volume, float pitch) {
        Location location = player.getLocation();
        // 在玩家的位置播放音效
        player.getWorld().playSound(location, sound, volume, pitch);
    }
}