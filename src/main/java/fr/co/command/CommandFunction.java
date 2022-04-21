package fr.co.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandFunction {

    boolean execute(CommandSender sender, Command command, String label, String[] args, String[] argsTrace, CommandHandler handler);

}
