package org.Little_100.GayMC_Plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopGUI {
    private final GayMC_Plugin plugin;

    public ShopGUI(GayMC_Plugin plugin) {
        this.plugin = plugin;
    }

    // 用于获取商品信息的方法
    public ItemData getItem(String key) {
        FileConfiguration config = plugin.getConfig();

        // 根据 key 获取配置中的名称、描述和价格
        String name = config.getString("Shop." + key + ".Name", "默认商品名称");
        String description = config.getString("Shop." + key + ".Description", "默认商品描述");
        int diamondPrice = config.getInt("Shop." + key + ".Price.Diamond", 1);
        int goldPrice = config.getInt("Shop." + key + ".Price.Gold", 1);
        int ironPrice = config.getInt("Shop." + key + ".Price.Iron", 1);

        // 创建一个价格的 Map
        Map<String, Integer> price = new HashMap<>();
        price.put("Diamond", diamondPrice);
        price.put("Gold", goldPrice);
        price.put("Iron", ironPrice);

        // 返回包含所有信息的 ItemData 对象
        return new ItemData(name, description, price);
    }

    public class ItemData {
        private final String name;
        private final String description;
        private final Map<String, Integer> price;

        public ItemData(String name, String description, Map<String, Integer> price) {
            this.name = name;
            this.description = description;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Map<String, Integer> getPrice() {
            return price;
        }

        public String getPriceString() {
            return String.format("价格: %d 钻石, %d 金锭, %d 铁锭",
                    price.get("Diamond"),
                    price.get("Gold"),
                    price.get("Iron"));
        }
    }
    public boolean purchase(Player player, String key) {
        // 获取对应的商品信息
        ItemData itemData = getItem(key);
        if (itemData == null) {
            player.sendMessage(ChatColor.RED + "未找到该商品。");
            return false;
        }

        // 获取商品的价格
        int diamondPrice = itemData.getPrice().get("Diamond");
        int goldPrice = itemData.getPrice().get("Gold");
        int ironPrice = itemData.getPrice().get("Iron");

        // 检查玩家是否有足够的物品
        if (!hasEnoughItems(player, Material.DIAMOND, diamondPrice) ||
                !hasEnoughItems(player, Material.GOLD_INGOT, goldPrice) ||
                !hasEnoughItems(player, Material.IRON_INGOT, ironPrice)) {

            player.sendMessage(ChatColor.RED + "你的资源不足，无法购买该商品。");
            return false;
        }

        // 扣除玩家的物品
        removeItems(player, Material.DIAMOND, diamondPrice);
        removeItems(player, Material.GOLD_INGOT, goldPrice);
        removeItems(player, Material.IRON_INGOT, ironPrice);

        // 发送成功购买的消息
        player.sendMessage(ChatColor.GREEN + "成功购买 " + itemData.getName() + "！");
        return true;
    }

    private boolean hasEnoughItems(Player player, Material material, int amount) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                count += item.getAmount();
            }
            if (count >= amount) {
                return true;
            }
        }
        return false;
    }

    private void removeItems(Player player, Material material, int amount) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                int itemAmount = item.getAmount();
                if (itemAmount <= amount) {
                    amount -= itemAmount;
                    player.getInventory().remove(item);
                } else {
                    item.setAmount(itemAmount - amount);
                    break;
                }
            }
        }
    }
}
