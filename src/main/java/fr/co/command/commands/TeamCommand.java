package fr.co.command.commands;

import fr.co.Teams;
import fr.co.command.CommandHandler;
import fr.co.command.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static java.lang.String.format;

public class TeamCommand {

    private static String name;

    public static void registerCommand(JavaPlugin plugin, String name) {
        TeamCommand.name = name;

        CommandHandler main = new CommandHandler(name, null, null, plugin);

        CommandHandler list = new CommandHandler("list", null, null, plugin, TeamCommand::list);

        CommandHandler remove = new CommandHandler("remove", null, null, plugin);
        CommandHandler removePlayer = new CommandHandler("player", null, null, plugin, TeamCommand::removePlayer);
        CommandHandler removeTeam = new CommandHandler("team", null, null, plugin, TeamCommand::removeTeam);

        CommandHandler add = new CommandHandler("add", null, null, plugin);
        CommandHandler addPlayer = new CommandHandler("player", null, null, plugin, TeamCommand::addPlayer);
        CommandHandler addTeam = new CommandHandler("team", null, null, plugin, TeamCommand::addTeam);

        remove.addSubCommand(removePlayer);
        remove.addSubCommand(removeTeam);

        add.addSubCommand(addPlayer);
        add.addSubCommand(addTeam);

        main.addSubCommand(remove);
        main.addSubCommand(add);
        main.addSubCommand(list);

        Objects.requireNonNull(plugin.getCommand(name)).setExecutor(main);
    }

    private static boolean addTeam(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        return false;
    }

    private static boolean removeTeam(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        return false;
    }

    private static boolean removePlayer(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        return false;
    }

    private static boolean list(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s list", TeamCommand.name), args, 0)) {
            commandSender.sendMessage(Teams.getInstance().getTeamsNames().toString());
        }
        return false;
    }

    private static boolean addPlayer(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s getall", TeamCommand.name), args, 1)) {
            if (Teams.getInstance().isTeamExist(args[0])) {
                commandSender.sendMessage(format("the team %s already exist !", args[0]));
                return false;
            }
            Teams.getInstance().addTeam(args[0]);
        }
        return false;
    }

}
