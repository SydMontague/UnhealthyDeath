package de.craftlancer.unhealthydeath;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class UnhealthyListener implements Listener
{
    private UnhealthyDeath plugin;
    
    public UnhealthyListener(UnhealthyDeath instance)
    {
        plugin = instance;
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        final Player p = event.getPlayer();
        UnhealthyGroup group = plugin.getGroup(p);
        
        if (group == null)
            return;
        
        if (!group.getWorlds().isEmpty() && !group.getWorlds().contains(p.getWorld().getName()))
            return;
        
        final int food = group.getNewFoodLevel(p);
        final double health = group.getNewHealthLevel(p);
        final float b = p.getSaturation();
        final float c = p.getExhaustion();
        
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                p.setSaturation(b);
                p.setExhaustion(c);
                p.setFoodLevel(food);
                p.setHealth(health);
            }
        }.runTaskLater(plugin, 1L);
    }
}
