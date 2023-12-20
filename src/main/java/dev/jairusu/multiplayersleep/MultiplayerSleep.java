package dev.jairusu.multiplayersleep;

import dev.jairusu.multiplayersleep.Commands.MainCommand;
import dev.jairusu.multiplayersleep.Events.SleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MultiplayerSleep extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Objects.requireNonNull(Bukkit.getPluginCommand("multiplayersleep")).setExecutor(new MainCommand());
        Bukkit.getPluginManager().registerEvents(new SleepEvent(), this);
        Bukkit.getLogger().info("[" + this.getName() + "] has enabled Successfully!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[" + this.getName() + "] has enabled Successfully!");
    }

}
