package fr.co.command.commands;

import fr.co.MineCrawl;
import fr.co.command.CommandHandler;
import fr.co.command.CommandUtils;
import fr.co.command.CompletionProvider;
import fr.co.economy.KillMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class KillMapCommand {

    private static String name;

    public static void registerCommand(JavaPlugin plugin, String name) {
        KillMapCommand.name = name;

        CommandHandler main = new CommandHandler(name, null, null, plugin);

        CommandHandler get = new CommandHandler(
                "get",
                null,
                new CompletionProvider[]{() -> KillMap.getInstance().getNames()},
                plugin,
                KillMapCommand::get
        );

        CommandHandler set = new CommandHandler(
                "set",
                null,
                new CompletionProvider[]{() -> Arrays.stream(EntityType.values()).map(Enum::name).toList()},
                plugin,
                KillMapCommand::set);

        main.addSubCommand(get);
        main.addSubCommand(set);

        Objects.requireNonNull(plugin.getCommand(name)).setExecutor(main);
    }

    private static boolean set(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {

        if (CommandUtils.basicCommandTest(commandSender, format("%s set", KillMapCommand.name), args, 2)) {
            try {
                EntityType entityType = EntityType.valueOf(args[0]);
                if (entityType == EntityType.UNKNOWN) {
                    commandSender.sendMessage(format("%s is not a valid entity type", args[0]));
                    return false;
                }

                try {
                    KillMap.getInstance().setPayment(entityType, Double.parseDouble(args[1]));
                    return true;

                } catch (NumberFormatException e) {
                    commandSender.sendMessage(format("%s is not a valid number", args[1]));
                }

            } catch (IllegalArgumentException e) {
                commandSender.sendMessage(format("%s is not a valid entity type", args[0]));
            }

        }

        return false;
    }

    private static boolean get(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s get", KillMapCommand.name), args, 1)) {
            try {
                EntityType entityType = EntityType.valueOf(args[0]);
                if (entityType == EntityType.UNKNOWN) {
                    commandSender.sendMessage(format("%s is not a valid entity type", args[0]));
                    return false;
                }
                commandSender.sendMessage(format("Killing %s is worth %s", args[0], KillMap.getInstance().getPayment(entityType)));
            } catch (IllegalArgumentException e) {
                commandSender.sendMessage(format("%s is not a valid entity type", args[0]));
            }
        }

        return true;
    }
}
