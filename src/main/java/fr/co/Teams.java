package fr.co;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * This class is used to manage teams. It is used to create and delete teams, add and remove players from teams.
 */
public class Teams {
    private static Teams instance;
    private final HashMap<String, List<OfflinePlayer>> teamMap = new HashMap<>();

    /**
     * Add a player to a team.
     * @param teamName The name of the team.
     * @param player The player to add.
     */
    public void addPlayerToTeam(String teamName, OfflinePlayer player) {
        if (teamMap.containsKey(teamName)) {
            teamMap.get(teamName).add(player);
        } else {
            teamMap.put(teamName, List.of(player));
        }
    }

    /**
     * Remove a player from a team.
     * @param teamName The name of the team.
     * @param player The player to remove.
     */
    public void removePlayerFromTeam(String teamName, OfflinePlayer player) {
        if (teamMap.containsKey(teamName)) {
            teamMap.get(teamName).remove(player);
        }
    }

    /**
     * Get the list of players in a team.
     * @param teamName The name of the team.
     * @return The list of players in the team.
     */
    public List<OfflinePlayer> getPlayersInTeam(String teamName) {
        return teamMap.get(teamName);
    }

    /**
     * check if the player is in a team.
     * @param teamName The name of the team.
     * @param player The player to check.
     * @return true if the player is in the team, false otherwise.
     */
    public boolean isPlayerInTeam(String teamName, OfflinePlayer player) {
        return teamMap.get(teamName).contains(player);
    }

    /**
     * check if the team exists.
     * @param teamName The name of the team.
     * @return true if the team exists, false otherwise.
     */
    public boolean isTeamExist(String teamName) {
        return teamMap.containsKey(teamName);
    }

    /**
     * Create a team. Creating an existing team will override it.
     * @param teamName The name of the team.
     */
    public void addTeam(String teamName) {
        teamMap.put(teamName, new ArrayList<>());
    }

    /**
     * Delete a team.
     * @param teamName The name of the team.
     */
    public void removeTeam(String teamName) {
        teamMap.remove(teamName);
    }

    /**
     * Get the list of teams names.
     * @return The list of teams names.
     */
    public List<String> getTeamsNames() {
        return new ArrayList<>(teamMap.keySet());
    }

    private Teams() {
    }

    public static Teams getInstance() {
        if (Teams.instance == null) {
            Teams.instance = new Teams();
        }
        return Teams.instance;
    }

    /**
     * Save the teams to the config.
     * @param config The config to save.
     */
    public void save(FileConfiguration config) {
        for (String teamName : teamMap.keySet()) {
            config.set("team." + teamName, teamMap.get(teamName).stream().map(OfflinePlayer::getUniqueId).map(UUID::toString).toArray());
        }
    }

    /**
     * Load the teams from the config.
     * @param config The config to load.
     */
    public void load(FileConfiguration config) {
        if (config.contains("team")) {
            for (String teamName : config.getConfigurationSection("team").getKeys(false)) {
                List<OfflinePlayer> players = new ArrayList<>();
                for (String uuid : config.getStringList("team." + teamName)) {
                    players.add(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
                }
                teamMap.put(teamName, players);
            }
        }
    }

}
