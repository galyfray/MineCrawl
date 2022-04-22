package fr.co.command.commands;

import fr.co.command.CommandHandler;
import fr.co.command.CompletionProvider;
import fr.co.economy.KillMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ZoneCommand {

    private static String name;

    public static void registerCommand(JavaPlugin plugin, String name) {
        ZoneCommand.name = name;

        CommandHandler main = new CommandHandler(name, null, null, plugin);

        CommandHandler setZone = new CommandHandler(
                "setZone",
                List.of("sz"),
                null,
                plugin,
                ZoneCommand::setZone
        );
    }

    private static boolean setZone(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler){
        return false;
    }

}
