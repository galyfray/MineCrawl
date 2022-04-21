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

    private static String name;

    public static void registerCommand(JavaPlugin plugin, String name) {
        BalanceCommand.name = name;

        CommandHandler main = new CommandHandler(name, null, null, plugin);

        CommandHandler get = new CommandHandler("get", null, null, plugin, BalanceCommand::get);

        CommandHandler set = new CommandHandler("set", null, new PlayerNameProvider[]{new PlayerNameProvider()}, plugin, BalanceCommand::set);

        CommandHandler pay = new CommandHandler("pay", null, new PlayerNameProvider[]{new PlayerNameProvider()}, plugin, BalanceCommand::pay);

        main.addSubCommand(get);
        main.addSubCommand(set);
        main.addSubCommand(pay);
    }

    private static boolean pay(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s pay", BalanceCommand.name), args, 2)) {
            OfflinePlayer player = Bukkit.getServer().getPlayer(args[0]);

            if (player == null) {
                commandSender.sendMessage(format("Unknown player %s", args[0]));
                return false;
            }

            int amount = Integer.parseInt(args[1]);

            if (amount <= 0) {
                commandSender.sendMessage("Amount must be positive");
                return false;
            }

            if (Balance.getInstance().getBalance((OfflinePlayer) commandSender) < amount) {
                commandSender.sendMessage("You don't have enough money");
                return false;
            } else {
                Balance.getInstance().debit((OfflinePlayer) commandSender, amount);
                Balance.getInstance().credit(player, amount);
            }

            return true;
        }
        return false;
    }

    private static boolean set(CommandSender commandSender, Command command, String label, String[] args, String[] argsTrace, CommandHandler commandHandler) {
        if (CommandUtils.basicCommandTest(commandSender, format("%s set", BalanceCommand.name), args, 2, 1, true)) {
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
        if (CommandUtils.basicCommandTest(commandSender, format("%s get", BalanceCommand.name), args, 0)) {
            if (args.length == 0) {
                commandSender.sendMessage("Votre solde est de " + Balance.getInstance().getBalance((OfflinePlayer) commandSender));
            } else if (args.length == 1) {
                OfflinePlayer player = Bukkit.getServer().getPlayer(args[0]);

                if (player == null) {
                    commandSender.sendMessage(format("Unknown player %s", args[0]));
                    return false;
                }

                commandSender.sendMessage(player.getName() + " a " + Balance.getInstance().getBalance(player));
            }
            return true;
        }
        return false;
    }
}
