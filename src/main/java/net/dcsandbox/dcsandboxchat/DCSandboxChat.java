package net.dcsandbox.dcsandboxchat;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DCSandboxChat extends JavaPlugin {

    private static DCSandboxChat instance;
    public static DCSandboxChat getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register chat listener
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        // Register commands
        Objects.requireNonNull(getCommand("msg")).setExecutor(new MsgCommand());
        Objects.requireNonNull(getCommand("reply")).setExecutor(new ReplyCommand());

        getLogger().info("Enabled DCSandboxChat version " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabled DCSandboxChat version " + getDescription().getVersion());
    }
}
