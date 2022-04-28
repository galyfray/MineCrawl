package fr.co.economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.UUID;

/**
 * A Bank class which register all the balance of the players
 */
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

    /**
     * Give to the player the amount of points
     * @param player the player which receive the points
     * @param points the amount of points
     */

    public void credit(OfflinePlayer player, double points) {
        double balance = getBalance(player);
        setBalance(player, balance + points);
    }

    /**
     * Take from the player the amount of points
     * @param player the player which loose the points
     * @param points the amount of points
     */
    public void debit(OfflinePlayer player, double points) {
        double balance = getBalance(player);
        setBalance(player, balance - points);
    }

    /**
     * Get the balance of the player
     * @param player the player
     * @return the balance of the player
     */
    public double getBalance(OfflinePlayer player) {
        return balances.getOrDefault(player, 0d);
    }

    /**
     * Set the balance of the player
     * @param player the player
     * @param points the new value of the balance
     */
    public void setBalance(OfflinePlayer player, double points) {
        balances.put(player, points);
    }

    /**
     * Save the states of the balances to the config file
     * @param config the config file
     */
    public void save(FileConfiguration config) {
        for (OfflinePlayer player : balances.keySet()) {
            config.set("balances." + player.getUniqueId(), balances.get(player));
        }
    }

    /**
     * Load the states of the balances from the config file
     * @param config the config file
     */
    public void load(FileConfiguration config) {
        if (config.contains("balances")) {
            for (String key : config.getConfigurationSection("balances").getKeys(false)) {
                balances.put(Bukkit.getOfflinePlayer(UUID.fromString(key)), config.getDouble("balances." + key));
            }
        }
    }
}
