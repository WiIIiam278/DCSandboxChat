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
                    targetPlayer.sendMessage("[" + player.getDisplayName() + "→You] " + ChatColor.GRAY + message);
                    player.sendMessage("[You→" + targetPlayer.getDisplayName() + "] " + ChatColor.GRAY + message);
                    conversations.put(player.getUniqueId(), targetPlayer.getUniqueId());
                    conversations.put(targetPlayer.getUniqueId(), player.getUniqueId());
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
}
