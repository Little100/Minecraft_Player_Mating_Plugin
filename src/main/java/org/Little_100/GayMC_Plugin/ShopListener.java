package org.Little_100.GayMC_Plugin;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopListener implements Listener {
    private final ShopGUI shopGUI;

    public ShopListener(ShopGUI shopGUI) {
        this.shopGUI = shopGUI;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        // 使用 getView().getTitle() 获取界面标题
        if (inventory != null && event.getView().getTitle().equals(ChatColor.GOLD + "商店")) {
            event.setCancelled(true); // 防止玩家拿走物品

            // 判断点击的物品
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

                if (itemName.equals(ChatColor.GREEN + "肾宝")) {
                    // 处理肾宝的购买逻辑
                    shopGUI.purchase(player, "SB");
                } else if (itemName.equals(ChatColor.GREEN + "杜蕾斯")) {
                    // 处理杜蕾斯的购买逻辑
                    shopGUI.purchase(player, "BYT");
                }
            }
        }
    }
}
