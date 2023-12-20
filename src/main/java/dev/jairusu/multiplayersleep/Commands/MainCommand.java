package dev.jairusu.multiplayersleep.Commands;

import dev.jairusu.multiplayersleep.Methods.Configuration;
import org.bukkit.GameRule;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MainCommand implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      if (!sender.isOp()) return new ArrayList<>();
      if (args.length == 1) return Arrays.asList("reload","type");
      if (args[0].equals("type") && args.length == 2) return Arrays.asList("onePlayer","sleepMost","default");
      return new ArrayList<>();
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!sender.isOp() && !(sender instanceof ConsoleCommandSender)) {
         sender.sendMessage("You don't have permission to use this command");
         return true;
      }

      if (args.length == 1 && args[0].equals("reload")) {
         Configuration.plugin.reloadConfig();
         sender.sendMessage("Configuration has been reloaded successfully");
         return true;
      }

      if (args[0].equals("type") && !(sender instanceof Player)) {
         sender.sendMessage("Sorry, only players can use this command");
         return true;
      }

      if (args.length == 2 && args[0].equals("type")) {
         Player player = (Player) sender;

         switch (args[1]) {
            case "onePlayer" -> player.getWorld().setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 0);
            case "sleepMost" -> player.getWorld().setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 30);
            case "default" -> player.getWorld().setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 100);
            default -> {
               sender.sendMessage("Please pick a valid option: <onePlayer>/<sleepMost>/<default>");
               return true;
            }
         }

         sender.sendMessage("Successfully changed the sleeping type to: " + args[1]);
         return true;
      }

      sender.sendMessage("Usage: /" + command + " <reload>/<percentage>");
      return true;
   }
}
