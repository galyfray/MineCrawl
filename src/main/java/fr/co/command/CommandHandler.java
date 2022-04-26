package fr.co.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class aims to represent the handler of a command or a subcommand.
 */
public class CommandHandler implements CommandExecutor, TabCompleter {

    private final String prefix;
    private final ArrayList<String> aliases = new ArrayList<>();
    private final ArrayList<CommandHandler> subCommands = new ArrayList<>();
    private final CompletionProvider[] providers;
    private final CommandFunction function;


    // 1 args constructor

    /**
     * @param prefix the prefix of the command or subcommand.
     *               ie : /mycommand subcommand here mycommand is the prefix of the command and subcommand is the prefix of the subcommand.
     */
    public CommandHandler(String prefix) {
        this(prefix, null, null, null, (CompletionProvider[]) null);
    }

    // 2 args constructor

    /**
     * @param prefix   the prefix of the command or subcommand.
     *                 ie : /mycommand subcommand here mycommand is the prefix of the command and subcommand is the prefix of the subcommand.
     * @param function The function to execute when the command is called if no matching subcommand is found.
     */
    public CommandHandler(String prefix, CommandFunction function) {
        this(prefix, null, null, function, (CompletionProvider[]) null);
    }

    // 3 args constructor

    /**
     * @param prefix    the prefix of the command or subcommand.
     *                  ie : /mycommand subcommand here mycommand is the prefix of the command and subcommand is the prefix of the subcommand.
     * @param function  The function to execute when the command is called if no matching subcommand is found.
     * @param providers A list of completion providers used to hints the user of the possible arguments.
     *                  It can be or contains null. If a provider is null, no completion will be proposed to the user. If the null provider is the last, it can be omitted
     */
    public CommandHandler(String prefix, CommandFunction function, CompletionProvider... providers) {
        this(prefix, null, null, function, providers);
    }

    @Deprecated
    public CommandHandler(String prefix, List<String> aliases, CompletionProvider[] providers, JavaPlugin plugin) {
        this(prefix, aliases, plugin, null, providers);
    }

    @Deprecated
    public CommandHandler(String prefix, List<String> aliases, CompletionProvider[] providers, JavaPlugin plugin, CommandFunction function) {
        this(prefix, aliases, plugin, function, providers);
    }

    /**
     * @param prefix    the prefix of the command or subcommand.
     *                  ie : /mycommand subcommand here mycommand is the prefix of the command and subcommand is the prefix of the subcommand.
     * @param aliases   the aliases of the command or subcommand. Aliases are not included in the default tab completion.
     * @param plugin    The plugin that include the command.
     *                  It is used to register the aliases of the command and can be safely null if there is no aliases or if you are registering a subcommand.
     * @param function  The function to execute when the command is called if no matching subcommand is found.
     * @param providers A list of completion providers used to hints the user of the possible arguments.
     *                  It can be or contains null. If a provider is null, no completion will be proposed to the user. If the null provider is the last, it can be omitted
     */
    public CommandHandler(String prefix, List<String> aliases, JavaPlugin plugin, CommandFunction function, CompletionProvider... providers) {
        if (plugin != null && aliases != null) {
            Command command = plugin.getCommand(prefix);
            if (command != null) {
                command.setAliases(aliases);
            }
        }
        if (aliases != null)
            this.aliases.addAll(aliases);
        this.aliases.add(prefix);
        this.prefix = prefix;
        this.providers = providers;
        this.function = function;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return onTabComplete(sender, command, alias, args, null);
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args, String[] argsTrace) {
        ArrayList<String> possibilities = new ArrayList<>();

        if (getSubCommands().size() != 0) {
            if (args.length == 1) {
                getSubCommands().forEach(subCommand -> possibilities.add(subCommand.getPrefix()));
            }

            for (CommandHandler cmd : getSubCommands()) {
                if (cmd.matches(args[0])) {
                    return cmd.onTabComplete(sender, command, alias, truncateArgs(args), generateArgsTrace(args, argsTrace));
                }
            }
        }

        if (providers != null && args.length <= providers.length && providers[args.length - 1] != null) {
            try {
                possibilities.addAll(providers[args.length - 1].getPossibilities(args[args.length - 1]));
            } catch (NullPointerException ignored) {

            }
        }

        return possibilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return onCommand(sender, command, label, args, null);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args, String[] argsTrace) {
        if (args.length > 0) {
            for (CommandHandler cmd : getSubCommands()) {
                if (cmd.matches(args[0])) {
                    return cmd.onCommand(sender, command, label, truncateArgs(args), generateArgsTrace(args, argsTrace));
                }
            }
        }
        if (function != null) {
            return function.execute(sender, command, label, args, argsTrace, this);
        }
        return false;
    }

    /**
     * Builds up the argsTrace array by adding the latest prefix to the argsTrace array.
     *
     * @param args      The latest args array.
     * @param argsTrace The argsTrace array. If null, a new array is created.
     * @return the new argsTrace array.
     */
    private String[] generateArgsTrace(String[] args, String[] argsTrace) {
        ArrayList<String> trace = new ArrayList<>();
        trace.add(args[0]);
        if (argsTrace != null)
            Collections.addAll(trace, argsTrace);
        return trace.toArray(new String[0]);
    }

    /**
     * Truncates the args array by removing already processed arguments such as subcommand names.
     *
     * @param args The args array.
     * @return the truncated args array.
     */
    public String[] truncateArgs(String[] args) {
        ArrayList<String> arg = new ArrayList<>();
        Collections.addAll(arg, args);
        arg.remove(args[0]);
        return arg.toArray(new String[0]);
    }

    public boolean matches(String alias) {
        return aliases.contains(alias);
    }

    public void addSubCommand(CommandHandler subCommand) {
        subCommands.add(subCommand);
    }

    public CompletionProvider[] getProviders() {
        return providers;
    }

    public List<CommandHandler> getSubCommands() {
        return Collections.unmodifiableList(subCommands);
    }

    public String getPrefix() {
        return prefix;
    }

}
