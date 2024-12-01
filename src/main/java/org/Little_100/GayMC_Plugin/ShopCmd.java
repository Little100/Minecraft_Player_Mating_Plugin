package org.Little_100.GayMC_Plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ShopCmd implements CommandExecutor {
    private final GayMC_Plugin plugin;
    private final ShopGUI shopGUI;

    public ShopCmd(GayMC_Plugin plugin, ShopGUI shopGUI) {
        this.plugin = plugin;
        this.shopGUI = shopGUI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("gshop")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openShopInventory(player);
            } else {
                sender.sendMessage(ChatColor.RED + "该命令只能由玩家执行。");
            }
            return true;
        }
        return false;
    }

    private void openShopInventory(Player player) {
        Inventory shopInventory = Bukkit.createInventory(null, 27, ChatColor.GREEN + "[=== GayMC_Plugin 商店 ===]");

        // SB 商品
        ItemStack sbItem = createShopItem("SB");
        shopInventory.addItem(sbItem);

        // BYT 商品
        ItemStack bytItem = createShopItem("BYT");
        shopInventory.addItem(bytItem);

        player.openInventory(shopInventory);
    }

    private ItemStack createShopItem(String key) {
        ShopGUI.ItemData itemData = shopGUI.getItem(key); // 从 shopGUI 获取对应的 ItemData
        ItemStack itemStack = new ItemStack(Material.DIAMOND); // 假设用钻石图标代表商品
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(itemData.getName());
            meta.setLore(Arrays.asList(itemData.getDescription(), "价格: " + itemData.getPriceString()));
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }
}