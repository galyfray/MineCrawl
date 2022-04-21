package fr.co;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MineCrawl extends JavaPlugin {

    public static Logger LOGGER;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        getLogger().info("MineCrawl is now enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("MineCrawl is now disabled");
    }
}
