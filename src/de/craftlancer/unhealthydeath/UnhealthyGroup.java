package de.craftlancer.unhealthydeath;

import java.util.List;

import org.bukkit.entity.Player;

public class UnhealthyGroup
{
    private double health;
    private boolean keepset;
    private int amount;
    private int minfood;
    private List<String> worlds;
    
    public UnhealthyGroup(double health, boolean keepset, int minfood, int amount, List<String> worlds)
    {
        this.setHealth(health);
        this.setKeepset(keepset);
        this.setMinfood(minfood);
        this.setAmount(amount);
        this.setWorlds(worlds);
    }

    public double getHealth()
    {
        return health;
    }

    public void setHealth(double health)
    {
        this.health = health;
    }

    public boolean isKeepset()
    {
        return keepset;
    }

    public void setKeepset(boolean keepset)
    {
        this.keepset = keepset;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getMinfood()
    {
        return minfood;
    }

    public void setMinfood(int minfood)
    {
        this.minfood = minfood;
    }

    public List<String> getWorlds()
    {
        return worlds;
    }

    public void setWorlds(List<String> worlds)
    {
        this.worlds = worlds;
    }

    public int getNewFoodLevel(Player p)
    {
        int food = isKeepset() ? getAmount() : p.getFoodLevel() - getAmount();
        food = food < getMinfood() && !isKeepset() ? getMinfood() : food;
        
        if(food < 0)
            food = 0;
        else if(food > 20)
            food = 20;
                
        return food;
    }

    public double getNewHealthLevel(Player p)
    {
        double tmp = getHealth();
        if(tmp <= 0)
            tmp = 1;        
        else if(tmp > p.getMaxHealth())
            tmp = p.getMaxHealth();
        
        return tmp;
    }
}
