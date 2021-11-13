package net.dcsandbox.dcsandboxchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private static final DCSandboxChat plugin = DCSandboxChat.getInstance();

    @EventHandler (ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        plugin.getLogger().info(e.getPlayer().getDisplayName() + ": " + e.getMessage());
        Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + ": " + ChatColor.GRAY + e.getMessage());
    }
}