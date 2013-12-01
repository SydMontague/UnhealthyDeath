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
        
        new DeathTask(p, food, health, b, c).runTaskLater(plugin, 1L);
    }
    
    private class DeathTask extends BukkitRunnable
    {
        private final Player p;
        private final int food;
        private final double health;
        private final float saturation;
        private final float exhaustion;
        
        public DeathTask(Player p, int food, double health, float saturation, float exhaustion)
        {
            this.p = p;
            this.food = food;
            this.health = health;
            this.saturation = saturation;
            this.exhaustion = exhaustion;
        }
        
        @Override
        public void run()
        {
            p.setSaturation(saturation);
            p.setExhaustion(exhaustion);
            p.setFoodLevel(food);
            p.setHealth(health);
        }
    }
}
