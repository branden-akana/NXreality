package com.octopod.nixium.nxreality;
import com.octopod.nixium.nxreality.NXreality;
import java.io.File;
import java.io.IOException;

import org.bukkit.block.Biome;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class NXRConfig{

    static private double configVers = 0.2;
    static private YamlConfiguration config;

    static public YamlConfiguration getConfig() {return config;}

    static public void tryWriteBiomeConfig() {
        try {

                File file = new File("plugins/nxReality/config.yml");

                YamlConfiguration newConfig = new YamlConfiguration();

                NXRPlugin.console("Generating config.yml...");

                newConfig.set("version", configVers);

                for(Biome biome:Biome.values()){
                        newConfig.set("biome."+biome.name()+".healthInterval", 0);
                        newConfig.set("biome."+biome.name()+".foodInterval", 0);
                        newConfig.set("biome."+biome.name()+".overridePerm", "");
                        newConfig.set("biome."+biome.name()+".overrideEquipment", new String[0]);
                }

                newConfig.set("altitude.256.healthInterval", 0);
                newConfig.set("altitude.256.foodInterval", 0);
                newConfig.set("altitude.256.overridePerm", "");
                newConfig.set("altitude.256.overrideEquipment", new String[0]);
                
                newConfig.set("water.healthInterval", 0);
                newConfig.set("water.foodInterval", 0);
                newConfig.set("water.overridePerm", "");
                newConfig.set("water.overrideEquipment", new String[0]);
                
                newConfig.set("regions.NXrealityRegion.healthInterval", 0);
                newConfig.set("regions.NXrealityRegion.foodInterval", 0);
                newConfig.set("regions.NXrealityRegion.overridePerm", "");
                newConfig.set("regions.NXrealityRegion.overrideEquipment", new String[0]);
                
                newConfig.save(file);

                config = newConfig;

                NXRPlugin.console("Success!");

        }catch (Exception e) {NXRPlugin.console("Unable to generate config.yml!");}
    }

    static public void tryReadBiomeConfig() {
        try{

            File file = new File("plugins/nxReality/config.yml");
            YamlConfiguration readConfig = new YamlConfiguration();
            readConfig.load(file);

            double version = Double.parseDouble(readConfig.get("version").toString());
            
            if(version < configVers){
                NXRPlugin.console("New config version! Regenerating...");
                tryWriteBiomeConfig();
            }else{
                NXRConfig.config = readConfig;
                NXRPlugin.console("Success!");                           
            }

        }catch (IOException | InvalidConfigurationException e) {
            NXRPlugin.console("Couldn't read config.yml, disabling plugin.");
            NXRPlugin.getPlugin().getPluginLoader().disablePlugin(NXRPlugin.getPlugin());
        }

    }

    public NXRConfig(NXreality plugin){

        File folder = new File("plugins/nxReality/");
        if(!folder.exists()){folder.mkdir();}

        File file = new File(folder, "config.yml");
        if(!file.exists()) {tryWriteBiomeConfig();}
        else{tryReadBiomeConfig();}

    }

}
