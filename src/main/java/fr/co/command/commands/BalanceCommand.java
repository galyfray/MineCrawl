package fr.co.command.commands;

import fr.co.command.CommandHandler;
import fr.co.command.CommandUtils;
import fr.co.command.completion.PlayerNameProvider;
import fr.co.points.Balance;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static java.lang.String.format;

public class BalanceCommand {
    public static void registerCommand(JavaPlugin plugin, String name) {
        CommandHandler main = new CommandHandler(name, null, null, plugin);

        CommandHandler get = new CommandHandler("get", null, null, plugin, BalanceCommand::get);

        CommandHandler set = new CommandHandler("set", null, new PlayerNameProvider[]{new PlayerNameProvider()}, plugin, BalanceCommand::set);


        main.addSubCommand(get);
        main.addSubCommand(set);
    }

    private static boolean set(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, "set", args, 2)) {
            OfflinePlayer player = Bukkit.getServer().getPlayer(args[0]);

            if (player == null) {
                commandSender.sendMessage(format("Unknown player %s", args[0]));
                return false;
            }

            Balance.getInstance().setBalance(player, Integer.parseInt(args[1]));
            return true;
        }
        return false;
    }

    private static boolean get(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        commandSender.sendMessage("Votre solde est de " + Balance.getInstance().getBalance((OfflinePlayer) commandSender));
        return true;
    }
}
