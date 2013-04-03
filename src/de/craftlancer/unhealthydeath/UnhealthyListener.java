package de.craftlancer.unhealthydeath;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class UnhealthyListener implements Listener
{
    protected UnhealthyDeath plugin;
    
    public UnhealthyListener(UnhealthyDeath instance)
    {
        plugin = instance;
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        final Player p = event.getPlayer();
        
        if ((!plugin.WORLDS.isEmpty() && !plugin.WORLDS.contains(p.getWorld().getName())) || p.hasPermission("unhealthydeath.exempt"))
            return;
        
        final int food = (plugin.FOOD_KEEPSET) ? plugin.SET_FOOD : p.getFoodLevel() - plugin.SUBTRACT_FOOD;
        final int health = plugin.SET_HEALTH;
        final float b = p.getSaturation();
        final float c = p.getExhaustion();
        
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                p.setSaturation(b);
                p.setExhaustion(c);
                
                if (food >= 20)
                    p.setFoodLevel(20);
                else if (plugin.MIN_FOOD >= food)
                    p.setFoodLevel(plugin.MIN_FOOD);
                else
                    p.setFoodLevel(food);
                
                if (health >= p.getMaxHealth())
                    p.setHealth(p.getMaxHealth());
                else
                    p.setHealth(health);
            }
        }, 1L);
    }
}
