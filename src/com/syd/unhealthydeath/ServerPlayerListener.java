package com.syd.unhealthydeath;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ServerPlayerListener implements Listener
{
    public static UnhealthyDeath plugin;

    public ServerPlayerListener(UnhealthyDeath instance)
    {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
        final Player p = e.getPlayer();
        final int food;
        final int health = plugin.config.getInt("health.sethealth");

        if (plugin.config.getString("food.foodchange").equalsIgnoreCase("set"))
            food = plugin.config.getInt("food.setfood");
        else if (plugin.config.getString("food.foodchange").equalsIgnoreCase("keep"))
            food = p.getFoodLevel() - plugin.config.getInt("food.substractfood");
        else
            food = 0;

        final float b = p.getSaturation();
        final float c = p.getExhaustion();
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
                if (food > 20)
                    p.setFoodLevel(20);
                else
                {
                    if (plugin.config.getInt("food.minfood") > food)
                        p.setFoodLevel(plugin.config.getInt("food.minfood"));
                    else
                        p.setFoodLevel(food);
                }

                p.setSaturation(b);
                p.setExhaustion(c);

                if (health <= 0 || health > 20)
                    p.setHealth(20);
                else
                    p.setHealth(health);
            }
        }, 1L);
    }
}
