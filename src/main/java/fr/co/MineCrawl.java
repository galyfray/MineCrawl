package fr.co;

import fr.co.command.commands.BalanceCommand;
import fr.co.command.commands.KillMapCommand;
import fr.co.command.commands.TeamCommand;
import fr.co.economy.KillMap;
import fr.co.events.EntityDeathEventHandler;
import fr.co.economy.Balance;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MineCrawl extends JavaPlugin {

    public List<Location> coordinate = new ArrayList<>();

    public static Logger LOGGER;

    @Override
    public void onEnable() {
        LOGGER = getLogger();

        // Commands registration

        BalanceCommand.registerCommand(this, "balance");
        KillMapCommand.registerCommand(this, "killmap");
        TeamCommand.registerCommand(this, "team");

        // Events registration

        getServer().getPluginManager().registerEvents(new EntityDeathEventHandler(), this);

        // Config loading
        Balance.getInstance().load(getConfig());
        KillMap.getInstance().load(getConfig());

        getLogger().info("MineCrawl is now enabled");
    }

    @Override
    public void onDisable() {
        Balance.getInstance().save(getConfig());
        KillMap.getInstance().save(getConfig());
        saveConfig();
        getLogger().info("MineCrawl is now disabled");
    }
}
