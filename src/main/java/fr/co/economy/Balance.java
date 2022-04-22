package fr.co.economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.UUID;

public class Balance {

    private static Balance instance;
    private static final HashMap<OfflinePlayer, Double> balances = new HashMap<>();

    private Balance() {
    }

    public static Balance getInstance() {
        if (instance == null) {
            instance = new Balance();
        }
        return instance;
    }

    public void credit(OfflinePlayer player, double points) {
        double balance = getBalance(player);
        setBalance(player, balance + points);
    }

    public void debit(OfflinePlayer player, double points) {
        double balance = getBalance(player);
        setBalance(player, balance - points);
    }

    public double getBalance(OfflinePlayer player) {
        return balances.getOrDefault(player, 0d);
    }

    public void setBalance(OfflinePlayer player, double points) {
        balances.put(player, points);
    }

    public void save(FileConfiguration config) {
        for (OfflinePlayer player : balances.keySet()) {
            config.set("balances." + player.getUniqueId(), balances.get(player));
        }
    }

    public void load(FileConfiguration config) {
        if (config.contains("balances")) {
            for (String key : config.getConfigurationSection("balances").getKeys(false)) {
                balances.put(Bukkit.getOfflinePlayer(UUID.fromString(key)), config.getDouble("balances." + key));
            }
        }
    }
}
