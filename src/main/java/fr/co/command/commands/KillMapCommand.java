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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class KillMapCommand {

    private static String name;

    public static void registerCommand(JavaPlugin plugin, String name) {
        KillMapCommand.name = name;

        CommandHandler main = new CommandHandler(name);

        CommandHandler get = new CommandHandler(
                "get",
                KillMapCommand::get,
                (prefix) -> KillMap.getInstance().getNames()
        );

        CommandHandler set = new CommandHandler(
                "set",
                KillMapCommand::set,
                (prefix) -> Arrays.stream(EntityType.values())
                        .map(Enum::name)
                        .filter(entry -> entry.startsWith(prefix))
                        .collect(Collectors.toList())
        );

        CommandHandler getAll = new CommandHandler("getall", KillMapCommand::getAll);

        main.addSubCommand(getAll);
        main.addSubCommand(get);
        main.addSubCommand(set);

        Objects.requireNonNull(plugin.getCommand(name)).setExecutor(main);
    }


    private static boolean getAll(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {

        if (CommandUtils.basicCommandTest(commandSender, format("%s getall", KillMapCommand.name), args, 0)) {
            StringBuilder msg = new StringBuilder();

            @SuppressWarnings("unchecked")
            Map.Entry<EntityType, Double>[] entries = KillMap.getInstance().getMap().entrySet().toArray(new Map.Entry[0]);

            int page = 0;

            if (args.length > 1) {
                try {
                    page = Integer.parseInt(args[0]);
                } catch (NumberFormatException ignored) {

                }
            }

            for (int i = page * 10; i < entries.length && i < (page + 1) * 10; i++) {
                msg.append(format("%s: %.2f\n", entries[i].getKey().name(), entries[i].getValue()));
            }

            msg.append(format("Page %d/%d", page + 1, (int) Math.ceil(entries.length / 10.0)));

            commandSender.sendMessage(msg.toString());

            return true;
        }

        return false;
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
