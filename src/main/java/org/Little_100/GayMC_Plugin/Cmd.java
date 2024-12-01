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

public class Cmd implements CommandExecutor {
    private final GayMC_Plugin plugin;
    private final ShopGUI shopGUI;

    public Cmd(GayMC_Plugin plugin) {
        this.plugin = plugin;
        this.shopGUI = new ShopGUI(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fuck")) {
            sender.sendMessage("Fuck u bitch!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("gshop")) {
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "请用/gshopgui打开商店...");
            } else {
                sender.sendMessage(ChatColor.RED + "该命令只能由玩家执行。");
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("gshopgui")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openShopInventory(player);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "该命令只能由玩家执行。");
            }
        }

        if (command.getName().equalsIgnoreCase("ghelp")) {
            sender.sendMessage(ChatColor.YELLOW + "===== GayMC_Plugin 帮助 =====");
            sender.sendMessage(ChatColor.GREEN + "/gshop - 打开购物界面");
            sender.sendMessage(ChatColor.GREEN + "/ghelp - 查看帮助");
            return true;
        }

        return false;
    }

    private void openShopInventory(Player player) {
        Inventory shopInventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "商店");

        // 加载并显示商品
        ShopGUI.ItemData sbData = shopGUI.getItem("SB");

        ItemStack sbItem = createItem(sbData);

        ShopGUI.ItemData bytData = shopGUI.getItem("BYT");
        ItemStack bytItem = createItem(bytData);

        shopInventory.setItem(11, sbItem);
        shopInventory.setItem(13, bytItem);

        player.openInventory(shopInventory);
    }

    private ItemStack createItem(ShopGUI.ItemData itemData) {
        ItemStack item = new ItemStack(Material.PAPER); // 这里你可以替换为你实际想要显示的物品
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + itemData.getName());
        meta.setLore(java.util.Arrays.asList(
                ChatColor.GRAY + itemData.getDescription(),
                ChatColor.YELLOW + itemData.getPriceString()
        ));
        item.setItemMeta(meta);
        return item;
    }
}