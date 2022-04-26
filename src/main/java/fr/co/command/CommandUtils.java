package fr.co.command;

import org.bukkit.command.CommandSender;

import fr.co.Localizer;

public class CommandUtils {

    private final static Localizer local = Localizer.getInstance();

    public static String getHelpMessage(String command) {
        return local.getLocalizedText(command.replaceAll(" ", "."));
    }

    /**
     * Utility method which runs a set of basic tests on a command.
     * Those tests are :
     * - the amount of arguments is correct (minArgsNumber and strict)
     * - the command is not a help command (helpLevel)
     * If the command is a help command, the help message is sent to the sender.
     * The help message is found in the dictionary file the key is the command name with dots instead of spaces.
     * If the command fails one of the tests, a generic error message is sent to the sender.
     *
     * @param sender        the sender of the command
     * @param command       the command name
     * @param args          the arguments of the command
     * @param minArgsNumber the minimum number of arguments
     * @return true if the test are passed, false otherwise
     */
    public static boolean basicCommandTest(CommandSender sender, String command, String[] args, int minArgsNumber) {
        return basicCommandTest(sender, command, args, minArgsNumber, 1, true);
    }

    /**
     * Utility method which runs a set of basic tests on a command.
     * Those tests are :
     * - the amount of arguments is correct (minArgsNumber and strict)
     * - the command is not a help command (helpLevel)
     * If the command is a help command, the help message is sent to the sender.
     * The help message is found in the dictionary file the key is the command name with dots instead of spaces.
     * If the command fails one of the tests, a generic error message is sent to the sender.
     *
     * @param sender        the sender of the command
     * @param command       the command name
     * @param args          the arguments of the command
     * @param minArgsNumber the minimum number of arguments
     * @param helpLevel     the number of the argument which should equals help to trigger the help command
     * @return true if the test are passed, false otherwise
     */
    public static boolean basicCommandTest(CommandSender sender, String command, String[] args, int minArgsNumber, int helpLevel) {
        return basicCommandTest(sender, command, args, minArgsNumber, helpLevel, true);
    }

    /**
     * Utility method which runs a set of basic tests on a command.
     * Those tests are :
     * - the amount of arguments is correct (minArgsNumber and strict)
     * - the command is not a help command (helpLevel)
     * If the command is a help command, the help message is sent to the sender.
     * The help message is found in the dictionary file the key is the command name with dots instead of spaces.
     * If the command fails one of the tests, a generic error message is sent to the sender.
     *
     * @param sender        the sender of the command
     * @param command       the command name
     * @param args          the arguments of the command
     * @param minArgsNumber the minimum number of arguments
     * @param strict        if true, the command will be considered as invalid if the amount of arguments is greater than minArgsNumber
     * @return true if the test are passed, false otherwise
     */
    public static boolean basicCommandTest(CommandSender sender, String command, String[] args, int minArgsNumber, boolean strict) {
        return basicCommandTest(sender, command, args, minArgsNumber, 1, strict);
    }

    /**
     * Utility method which runs a set of basic tests on a command.
     * Those tests are :
     * - the amount of arguments is correct (minArgsNumber and strict)
     * - the command is not a help command (helpLevel)
     * If the command is a help command, the help message is sent to the sender.
     * The help message is found in the dictionary file the key is the command name with dots instead of spaces.
     * If the command fails one of the tests, a generic error message is sent to the sender.
     *
     * @param sender        the sender of the command
     * @param command       the command name
     * @param args          the arguments of the command
     * @param minArgsNumber the minimum number of arguments
     * @param helpLevel     the number of the argument which should equals help to trigger the help command
     * @param strict        if true, the command will be considered as invalid if the amount of arguments is greater than minArgsNumber
     * @return true if the test are passed, false otherwise
     */
    public static boolean basicCommandTest(CommandSender sender, String command, String[] args, int minArgsNumber, int helpLevel, boolean strict) {
        if (args.length == helpLevel && args[helpLevel - 1].equals("help")) {
            sender.sendMessage(getHelpMessage(command));
        }
        if (strict) {
            if (args.length != minArgsNumber) {
                sender.sendMessage(String.format("Wrong command usage see /%s help for more information", command));
                return false;
            }
        } else {
            if (args.length < minArgsNumber) {
                sender.sendMessage(String.format("Wrong command usage see /%s help for more information", command));
                return false;
            }
        }
        return true;
    }
}
