
package com.octopod.nixium.nxreality.worldeffects;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.PlayerInventory;

public class EffectWG extends NXRWorldEffects{
    
    private ProtectedRegion region;
    
    public EffectWG(ProtectedRegion region){
        super();
        this.region = region;
    }
    
    @Override
    public int getHealthInterval(){
       try{return getConfig().getInt("water.healthInterval");} catch(NullPointerException e) {return 0;}
    }
    
    @Override
    public int getFoodInterval(){
       try{return getConfig().getInt("water.foodInterval");} catch(NullPointerException e) {return 0;}
    }
    
    @Override
    public String[] getOverrideEquipment(){
        try{return (String[])getConfig().getStringList("water.overrideEquipment").toArray();} catch(NullPointerException e) {return new String[0];}
    }
    
    @Override
    public String getOverridePerm(){
        try{return getConfig().get("water.overridePerm").toString();} catch(NullPointerException e) {return "";}
    }    
    
    @Override
    public boolean playerCanOverride(Player player){
        boolean override = true;

        String node = getOverridePerm();
        if(node.equals("") || !player.hasPermission(node)) {override = false;}
        else{
            PlayerInventory inv = player.getInventory();
            
            String helmetDisplay = ChatColor.stripColor(inv.getHelmet().getItemMeta().getDisplayName()).toLowerCase();
            String chestDisplay = ChatColor.stripColor(inv.getChestplate().getItemMeta().getDisplayName()).toLowerCase();
            String legDisplay = ChatColor.stripColor(inv.getLeggings().getItemMeta().getDisplayName()).toLowerCase();
            String bootDisplay = ChatColor.stripColor(inv.getBoots().getItemMeta().getDisplayName()).toLowerCase();

            for(String equipment:getOverrideEquipment()) {
                if(
                    !helmetDisplay.equals(equipment.toLowerCase()) &&
                    !chestDisplay.equals(equipment.toLowerCase()) && 
                    !legDisplay.equals(equipment.toLowerCase()) &&
                    !bootDisplay.equals(equipment.toLowerCase())
                ) {
                    override = false; 
                    break;
                }
            }
        }
        return override;
    }
    
    static public ProtectedRegion getPrioritizedRegion(Location loc) {
        ApplicableRegionSet set = WGBukkit.getRegionManager(loc.getWorld()).getApplicableRegions(loc);

        int highestPriority = -1;
        ProtectedRegion highestRegion = null;
        for(ProtectedRegion region: set){
            if(region.getPriority() >= highestPriority) {
                highestPriority = region.getPriority();
                highestRegion = region;
            }
        }

        return highestRegion;
    }
    
}
