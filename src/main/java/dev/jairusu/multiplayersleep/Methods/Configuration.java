package dev.jairusu.multiplayersleep.Methods;

import dev.jairusu.multiplayersleep.MultiplayerSleep;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

   public static JavaPlugin plugin = JavaPlugin.getPlugin(MultiplayerSleep.class);

   public static String getString(String key) {
      return plugin.getConfig().getString(key);
   }

   public static Component transform(String string) {
      MiniMessage miniMessage = MiniMessage.miniMessage();
      return miniMessage.deserialize(string).decoration(TextDecoration.ITALIC,false);
   }

   public static List<String> worldGroups(World world) {
      ConfigurationSection section = plugin.getConfig().getConfigurationSection("worldGroups");
      if (section == null) return new ArrayList<>();
      for (String groupName : section.getKeys(false)) {
         List<String> worldNames = section.getStringList(groupName);
         if (!worldNames.contains(world.getName())) continue;
         return worldNames;
      }
      return new ArrayList<>();
   }

}
