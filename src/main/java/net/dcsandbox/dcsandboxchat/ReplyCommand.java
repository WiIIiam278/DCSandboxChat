package net.dcsandbox.dcsandboxchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.StringJoiner;

public class ReplyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length >= 1) {
                if (MsgCommand.conversations.containsKey(player.getUniqueId())) {
                    Player targetPlayer = Bukkit.getPlayer(MsgCommand.conversations.get(player.getUniqueId()));
                    if (targetPlayer == null) {
                        player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "That player is no longer online.");
                        return true;
                    }
                    if (targetPlayer.getUniqueId().equals(player.getUniqueId())) {
                        player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You can't reply to yourself!");
                        return true;
                    }
                    StringJoiner message = new StringJoiner(" ");
                    for (String messageItem : args) {
                        message.add(messageItem);
                    }
                    targetPlayer.sendMessage("[" + player.getDisplayName() + ChatColor.GRAY + "→" + ChatColor.GRAY + "You] " + ChatColor.GRAY + message);
                    player.sendMessage("[You" + ChatColor.GRAY + "→" + ChatColor.GRAY + targetPlayer.getDisplayName() + "] " + ChatColor.GRAY + message);
                    MsgCommand.conversations.put(player.getUniqueId(), targetPlayer.getUniqueId());
                    MsgCommand.conversations.put(targetPlayer.getUniqueId(), player.getUniqueId());
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You have nobody to reply to!");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid syntax. Usage: " + command.getUsage());
            }
            return true;
        }
        return false;
    }
}
