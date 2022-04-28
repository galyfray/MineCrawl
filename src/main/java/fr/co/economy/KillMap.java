package fr.co.economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to store the kill map. The kill map maps all entity types to the number of points earned for each kill.
 */
public class KillMap {
    private static KillMap instance = null;
    private final HashMap<EntityType, Double> killMap = new HashMap<>();

    private KillMap() {
    }

    public static KillMap getInstance() {
        if (instance == null) {
            instance = new KillMap();
        }
        return instance;
    }

    /**
     * This method is used to get the number of points earned for killing a specific entity type.
     * @param type The entity type to get the points for.
     * @return The number of points earned for killing the entity type.
     */
    public double getPayment(EntityType type) {
        return killMap.getOrDefault(type, 0.0d);
    }

    /**
     * This method is used to set the number of points earned for killing a specific entity type.
     * @param type The entity type to set the points for.
     * @param value The number of points earned for killing the entity type.
     */
    public void setPayment(EntityType type, double value) {
        if (value == 0){
            killMap.remove(type);
        }
        killMap.put(type, value);
    }

    /**
     * This method is used to remove an entity type from the kill map.
     * This is equivalent to setting the number of points earned for killing the entity type to 0.
     */
    public void remove(EntityType type) {
        killMap.remove(type);
    }

    /**
     * This method is used to get the kill map.
     * @return The kill map.
     */
    public Map<EntityType, Double> getMap() {
        return Collections.unmodifiableMap(killMap);
    }

    /**
     * This methods allow you to get the list of the entity types names that are in the kill map and have a value other than 0.
     * @return The list of the entity types names that are in the kill map and have a value other than 0.
     */
    public List<String> getNames() {
        List<String> list = Collections.emptyList();
        if (killMap.size() > 0) {
            list = killMap.keySet().stream()
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }
        return list;
    }

    /**
     * This method is used to save the kill map to the config file.
     * @param config The config file to save the kill map to.
     */
    public void save(FileConfiguration config) {
        for (Map.Entry<EntityType, Double> entry : killMap.entrySet()) {
            config.set("kills." + entry.getKey().name(), entry.getValue());
        }
    }

    /**
     * This method is used to load the kill map from the config file.
     * @param config The config file to load the kill map from.
     */
    public void load(FileConfiguration config) {
        for (EntityType type : EntityType.values()) {
            if (config.contains("kills." + type.name())) {
                killMap.put(type, config.getDouble("kills." + type.name()));
            }
        }
    }
}
