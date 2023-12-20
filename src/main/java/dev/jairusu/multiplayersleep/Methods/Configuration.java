package dev.jairusu.multiplayersleep.Methods;

import dev.jairusu.multiplayersleep.MultiplayerSleep;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {

   public static JavaPlugin plugin = JavaPlugin.getPlugin(MultiplayerSleep.class);

   public static String getString(String key) {
      return plugin.getConfig().getString(key);
   }

   public static ConfigurationSection getConfigSection(String key) {
      return plugin.getConfig().getConfigurationSection(key);
   }

   public static Component transform(String string) {
      MiniMessage miniMessage = MiniMessage.miniMessage();
      return miniMessage.deserialize(string).decoration(TextDecoration.ITALIC,false);
   }

}
