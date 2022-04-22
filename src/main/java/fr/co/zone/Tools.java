package fr.co.zone;

import fr.co.MineCrawl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static void saveZone(List<Zone> zones, MineCrawl mc){
        /**
         * Fonction qui permet de sauvegarder une liste de zonne dans le fichier de config
         * le fichier de config aura cette forme :
         * zone.{nom de la zonne}.(cooNeg|cooPos).(x|y|z)
         */
        for(Zone zone : zones){
            mc.getConfig().set("zone." + zone.getName() + ".world",zone.getCooNeg().getWorld().getName());

            mc.getConfig().set("zone." + zone.getName() + ".cooNeg.x",zone.getCooNeg().getBlockX());
            mc.getConfig().set("zone." + zone.getName() + ".cooNeg.y",zone.getCooNeg().getBlockY());
            mc.getConfig().set("zone." + zone.getName() + ".cooNeg.z",zone.getCooNeg().getBlockZ());

            mc.getConfig().set("zone." + zone.getName() + ".cooPos.x",zone.getCooPos().getBlockX());
            mc.getConfig().set("zone." + zone.getName() + ".cooPos.y",zone.getCooPos().getBlockY());
            mc.getConfig().set("zone." + zone.getName() + ".cooPos.z",zone.getCooPos().getBlockZ());
        }
    }

    public static List<Zone> loadZone(MineCrawl mc){
        /**
         * Fonction permetant le rechargement des zone depuis le fichier de config
         */

        List<Zone> zones = new ArrayList<>();
        FileConfiguration config = mc.getConfig();

        if (config.contains("zone")) {
            for (String key : config.getConfigurationSection("zone").getKeys(false)) {

                String world = config.getString("zone." + key + ".world");
                int xNeg = config.getInt("zone." + key + ".cooNeg.x");
                int yNeg = config.getInt("zone." + key + ".cooNeg.y");
                int zNeg = config.getInt("zone." + key + ".cooNeg.z");
                Location cooNeg = new Location(Bukkit.getWorld(world),xNeg,yNeg,zNeg);

                int xPos = config.getInt("zone." + key + ".cooPos.x");
                int yPos = config.getInt("zone." + key + ".cooPos.y");
                int zPos = config.getInt("zone." + key + ".cooPos.z");
                Location cooPos = new Location(Bukkit.getWorld(world),xPos,yPos,zPos);

                String name = key;

                zones.add(new Zone(cooNeg,cooPos,name));

            }
        }
        return zones;
    }
}
