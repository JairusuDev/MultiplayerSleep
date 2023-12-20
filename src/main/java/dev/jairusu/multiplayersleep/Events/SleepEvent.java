package dev.jairusu.multiplayersleep.Events;

import dev.jairusu.multiplayersleep.Methods.Configuration;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class SleepEvent implements Listener {

   @EventHandler
   public void onPlayerDeepSleep(PlayerDeepSleepEvent event) {
      Player player = event.getPlayer();
      World world = player.getWorld();

      double sleepingPlayers = (double) world.getPlayers().stream().filter(Player::isSleeping).count();
      int onlinePlayers = world.getPlayers().size();
      double percentage = (sleepingPlayers / onlinePlayers) * 100;
      if (requiredPlayers(world) > percentage) return;

      for (Player sleeping : world.getPlayers()) {
         if (sleeping.isSleeping()) sleeping.wakeup(false);
      }

      Bukkit.getScheduler().runTaskLater(Configuration.plugin, () -> {
         world.setStorm(false);
         world.setThundering(false);
         dayNightAnimation(world);
         if (requiredPlayers(world) == 0) sendOnePlayerSleepMessage(player);
         else sendPlayerSleepMessage(world);
      }, 1L);
   }

   private double requiredPlayers(World world) {
      Integer sleepingPercentage = world.getGameRuleValue(GameRule.PLAYERS_SLEEPING_PERCENTAGE);
      if (sleepingPercentage == null) return 0;
      if (sleepingPercentage != 0 && sleepingPercentage != 30 && sleepingPercentage != 100) {
         world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 100);
         return 100;
      }
      return (double) sleepingPercentage;
   }

   private void dayNightAnimation(World playerWorld) {
      final BukkitTask[] task = new BukkitTask[1];
      task[0] = Bukkit.getScheduler().runTaskTimer(Configuration.plugin, () -> {
         if (playerWorld.getTime() < 12010) {
            task[0].cancel();
            return;
         }
         playerWorld.setTime(playerWorld.getTime() + 100);
      }, 1L, 1L);
   }

   private void sendPlayerSleepMessage(World world) {
      List<String> worldGroups = Configuration.worldGroups(world);
      String sleepMessage = Configuration.getString("message.playerSleepMessage");
      if (sleepMessage == null || sleepMessage.isEmpty()) return;
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroups.contains(onlinePlayer.getWorld().getName())) continue;
         onlinePlayer.sendMessage(Configuration.transform(sleepMessage));
      }
   }

   private void sendOnePlayerSleepMessage(Player player) {
      List<String> worldGroups = Configuration.worldGroups(player.getWorld());
      String sleepMessage = Configuration.getString("message.onePlayerSleepMessage");
      if (sleepMessage == null || sleepMessage.isEmpty()) return;
      sleepMessage = sleepMessage.replace("%player%",player.getName());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroups.contains(onlinePlayer.getWorld().getName())) continue;
         onlinePlayer.sendMessage(Configuration.transform(sleepMessage));
      }
   }

}
