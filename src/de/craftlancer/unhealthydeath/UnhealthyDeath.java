package de.craftlancer.unhealthydeath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

//TODO Permission Support
public class UnhealthyDeath extends JavaPlugin
{
    private UnhealthyListener listener;
    protected Logger log;
    protected FileConfiguration config;
    
    protected List<String> WORLDS = new ArrayList<String>();
    protected int SET_HEALTH = 20;
    protected boolean FOOD_KEEPSET = false; // false = keep, true = set
    protected int MIN_FOOD = 0;
    protected int SET_FOOD = 20;
    protected int SUBTRACT_FOOD = 0;
    
    @Override
    public void onEnable()
    {
        log = getLogger();
        
        loadConfig();
        
        listener = new UnhealthyListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
    }
    
    @Override
    public void onDisable()
    {
        config = null;
        getServer().getScheduler().cancelTasks(this);
    }
    
    private void loadConfig()
    {
        if (!new File(getDataFolder().getPath(), "config.yml").exists())
            saveDefaultConfig();
        
        config = getConfig();
        
        SET_HEALTH = validate(config.getInt("health.sethealth", 20), 1, 2147483647, false);
        FOOD_KEEPSET = config.getString("food.foodchange", "keep").equalsIgnoreCase("set");
        MIN_FOOD = validate(config.getInt("food.minfood", 0), 0, 20, true);
        SET_FOOD = validate(config.getInt("food.setfood", 20), 0, 20, false);
        SUBTRACT_FOOD = config.getInt("food.subtractfood", 0);
        SUBTRACT_FOOD = (SUBTRACT_FOOD == 0 && !FOOD_KEEPSET) ? config.getInt("food.substractfood", 0) : 0;
        
        WORLDS = config.getStringList("worlds");
    }
    
    private static int validate(int input, int min, int max, boolean usemin)
    {
        if (input > max || input < min)
            return usemin ? min : max;
        else
            return input;
    }
}
