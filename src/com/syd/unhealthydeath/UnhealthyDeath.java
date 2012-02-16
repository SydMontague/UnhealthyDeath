package com.syd.unhealthydeath;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class UnhealthyDeath extends JavaPlugin
{
    public final Logger log = Logger.getLogger("Minecraft");
    ServerPlayerListener PlayerListener = new ServerPlayerListener(this);
    FileConfiguration config;
    
    public void onEnable()
    {
        config = getConfig();     
        saveDefaultConfig();        
        getServer().getPluginManager().registerEvents(PlayerListener, this);
        log.info("[UnhealthDeath] " + getDescription().getVersion() + " enabled!");
    }

    public void onDisable()
    {
        config = null;
    }
}
