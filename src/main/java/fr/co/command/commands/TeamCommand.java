package fr.co.command.commands;

import fr.co.Teams;
import fr.co.command.CommandHandler;
import fr.co.command.CommandUtils;
import fr.co.command.completion.PlayerNameProvider;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static java.lang.String.format;

public class TeamCommand {

    private static String name;

    public static void registerCommand(JavaPlugin plugin, String name) {
        TeamCommand.name = name;

        CommandHandler main = new CommandHandler(name);

        CommandHandler list = new CommandHandler(
                "list",
                TeamCommand::list,
                (prefix) -> Teams.getInstance().getTeamsNames()
        );

        CommandHandler remove = new CommandHandler("remove");
        CommandHandler removePlayer = new CommandHandler(
                "player",
                TeamCommand::removePlayer,
                (prefix) -> Teams.getInstance().getTeamsNames(),
                new PlayerNameProvider()
        );
        CommandHandler removeTeam = new CommandHandler(
                "team",
                TeamCommand::removeTeam,
                (prefix) -> Teams.getInstance().getTeamsNames()
        );

        CommandHandler add = new CommandHandler("add");
        CommandHandler addPlayer = new CommandHandler(
                "player",
                TeamCommand::addPlayer,
                (prefix) -> Teams.getInstance().getTeamsNames(),
                new PlayerNameProvider()
        );
        CommandHandler addTeam = new CommandHandler(
                "team",
                TeamCommand::addTeam
        );

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
        if (CommandUtils.basicCommandTest(commandSender, format("%s add team", TeamCommand.name), args, 1)) {
            if (Teams.getInstance().isTeamExist(args[0])) {
                commandSender.sendMessage(format("The team %s already exist !", args[0]));
                return false;
            }
            Teams.getInstance().addTeam(args[0]);
            commandSender.sendMessage(format("The team %s has been created !", args[0]));
            return true;
        }
        return false;
    }

    private static boolean removeTeam(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s add team", TeamCommand.name), args, 1)) {
            if (!Teams.getInstance().isTeamExist(args[0])) {
                commandSender.sendMessage(format("the team %s already exist !", args[0]));
                return false;
            }
            Teams.getInstance().removeTeam(args[0]);
            commandSender.sendMessage(format("The team %s has been removed !", args[0]));
            return true;
        }
        return false;
    }

    private static boolean removePlayer(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s remove player", TeamCommand.name), args, 2)) {
            if (!Teams.getInstance().isTeamExist(args[0])) {
                commandSender.sendMessage(format("the team %s does not exist !", args[0]));
                return false;
            }
            OfflinePlayer player = Bukkit.getServer().getPlayer(args[1]);

            if (player == null) {
                commandSender.sendMessage(format("Unknown player %s", args[1]));
                return false;
            }

            Teams.getInstance().removePlayerFromTeam(args[0], player);

            commandSender.sendMessage(format("Successfully removed %s to team %s", player.getName(), args[0]));
            return true;
        }
        return false;
    }

    private static boolean list(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s list", TeamCommand.name), args, 0, false)) {
            if (args.length == 0) {
                commandSender.sendMessage(Teams.getInstance().getTeamsNames().toString());
            } else {
                if (Teams.getInstance().isTeamExist(args[0])) {
                    commandSender.sendMessage(Teams.getInstance().getPlayersInTeam(args[0]).toString());
                } else {
                    commandSender.sendMessage(format("The team %s does not exist !", args[0]));
                }
            }
        }
        return false;
    }

    private static boolean addPlayer(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s add player", TeamCommand.name), args, 2)) {
            if (!Teams.getInstance().isTeamExist(args[0])) {
                commandSender.sendMessage(format("the team %s does not exist !", args[0]));
                return false;
            }
            OfflinePlayer player = Bukkit.getServer().getPlayer(args[1]);

            if (player == null) {
                commandSender.sendMessage(format("Unknown player %s", args[1]));
                return false;
            }

            Teams.getInstance().addPlayerToTeam(args[0], player);

            commandSender.sendMessage(format("Successfully added %s to team %s", player.getName(), args[0]));
            return true;
        }
        return false;
    }

}
