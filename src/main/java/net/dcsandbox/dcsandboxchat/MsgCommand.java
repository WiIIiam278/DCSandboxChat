package net.dcsandbox.dcsandboxchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.StringJoiner;
import java.util.UUID;

public class MsgCommand implements CommandExecutor {

    private static final DCSandboxChat plugin = DCSandboxChat.getInstance();

    // Map of conversations for reply feature
    public static HashMap<UUID,UUID> conversations = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length >= 2) {
                String playerName = args[0];
                Player targetPlayer = Bukkit.getPlayer(playerName);
                StringJoiner message = new StringJoiner(" ");
                int counter = 0;
                for (String messageItem : args) {
                    if (counter < 1) {
                        counter++;
                        continue;
                    }
                    message.add(messageItem);
                }
                if (targetPlayer != null) {
                    if (targetPlayer.getUniqueId().equals(player.getUniqueId())) {
                        player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You can't message yourself!");
                        return true;
                    }
                    sendPrivateMessage(player, targetPlayer, message);
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid player.");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid syntax. Usage: " + command.getUsage());
            }
            return true;
        }
        return false;
    }

    // Dispatch a private message
    public static void sendPrivateMessage(Player player, Player targetPlayer, StringJoiner message) {
        targetPlayer.sendMessage("[" + player.getDisplayName() + ChatColor.GRAY + "→" + ChatColor.GRAY + "You] " + ChatColor.GRAY + message);
        player.sendMessage("[You"+ ChatColor.GRAY + "→" + ChatColor.GRAY + targetPlayer.getDisplayName() + "] " + ChatColor.GRAY + message);
        plugin.getLogger().info(player.getDisplayName() + " → " + targetPlayer.getDisplayName() + ": " + message);
        conversations.put(player.getUniqueId(), targetPlayer.getUniqueId());
        conversations.put(targetPlayer.getUniqueId(), player.getUniqueId());
    }
}
