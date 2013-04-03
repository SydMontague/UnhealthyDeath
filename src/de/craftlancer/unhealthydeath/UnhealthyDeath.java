package de.craftlancer.unhealthydeath;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
//TODO Multiworld Support
//TODO Permission Support
public class UnhealthyDeath extends JavaPlugin
{
    private UnhealthyListener listener = new UnhealthyListener(this);
    protected Logger log;
    protected FileConfiguration config;
    protected Set<String> worlds = new HashSet<String>();
    
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
