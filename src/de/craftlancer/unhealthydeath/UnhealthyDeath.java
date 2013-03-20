package de.craftlancer.unhealthydeath;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class UnhealthyDeath extends JavaPlugin
{
    public Logger log;
    UnhealthyListener listener = new UnhealthyListener(this);
    FileConfiguration config;
    
    @Override
    public void onEnable()
    {
        log = getLogger();
        
        if (!new File(this.getDataFolder().getPath(), "config.yml").exists())
            saveDefaultConfig();
        
        config = getConfig();
        
        getServer().getPluginManager().registerEvents(listener, this);
    }
    
    @Override
    public void onDisable()
    {
        config = null;
        getServer().getScheduler().cancelTasks(this);
    }
}
