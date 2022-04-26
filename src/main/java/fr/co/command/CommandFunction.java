package fr.co.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandFunction {
    /**
     * This method is called when the command is executed. His goal is to store all the logic of the command.
     *
     * @param sender    The sender of the command. Can be a player, a console or a command block.
     * @param command   The command that was executed.
     * @param label     The alias used to call the original command.
     * @param args      The arguments of the command.
     * @param argsTrace The arguments of the previous layer of logic
     * @param handler   The command handler that called this function.
     * @return true if the command was executed successfully, false otherwise.
     */
    boolean execute(CommandSender sender, Command command, String label, String[] args, String[] argsTrace, CommandHandler handler);

}
