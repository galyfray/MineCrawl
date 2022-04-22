package fr.co.economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public double getPayment(EntityType type) {
        return killMap.getOrDefault(type, 0.0d);
    }

    public void setPayment(EntityType type, double value) {
        killMap.put(type, value);
    }

    public void remove(EntityType type) {
        killMap.remove(type);
    }

    public Map<EntityType, Double> getMap() {
        return Collections.unmodifiableMap(killMap);
    }

    public List<String> getNames() {
        List<String> list = Collections.emptyList();
        if (killMap.size() > 0) {
            list = killMap.keySet().stream()
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }
        return list;
    }

    public void save(FileConfiguration config) {
        for (Map.Entry<EntityType, Double> entry : killMap.entrySet()) {
            config.set("kills." + entry.getKey().name(), entry.getValue());
        }
    }

    public void load(FileConfiguration config) {
        for (EntityType type : EntityType.values()) {
            if (config.contains("kills." + type.name())) {
                killMap.put(type, config.getDouble("kills." + type.name()));
            }
        }
    }
}
