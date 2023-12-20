package dev.jairusu.multiplayersleep;

import dev.jairusu.multiplayersleep.Commands.MainCommand;
import dev.jairusu.multiplayersleep.Events.SleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MultiplayerSleep extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        PluginCommand mainCommand = Objects.requireNonNull(Bukkit.getPluginCommand("multiplayersleep"));
        mainCommand.setExecutor(new MainCommand());
        mainCommand.setTabCompleter(new MainCommand());
        Bukkit.getPluginManager().registerEvents(new SleepEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
