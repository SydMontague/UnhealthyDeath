package de.craftlancer.unhealthydeath;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class UnhealthyListener implements Listener
{
    public static UnhealthyDeath plugin;

    public UnhealthyListener(UnhealthyDeath instance)
    {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {        
        final Player p = event.getPlayer();
        final int food;
        final int health = plugin.config.getInt("health.sethealth");
        
        final float b = p.getSaturation();
        final float c = p.getExhaustion();
        
        if (plugin.config.getString("food.foodchange").equalsIgnoreCase("set"))
            food = plugin.config.getInt("food.setfood");
        else if (plugin.config.getString("food.foodchange").equalsIgnoreCase("keep"))
            food = p.getFoodLevel() - plugin.config.getInt("food.substractfood");
        else
            food = 0;
        
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                p.setSaturation(b);
                p.setExhaustion(c);
                
                if (food > 20)
                    p.setFoodLevel(20);
                else
                {
                    if (plugin.config.getInt("food.minfood") > food)
                        p.setFoodLevel(plugin.config.getInt("food.minfood"));
                    else
                        p.setFoodLevel(food);
                }
                
                if (health <= 0 || health > p.getMaxHealth())
                    p.setHealth(p.getMaxHealth());
                else
                    p.setHealth(health);
            }
        }, 1L);
    }
}
