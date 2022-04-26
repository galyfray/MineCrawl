package fr.co;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Teams {
    private static Teams instance;
    private final HashMap<String, List<OfflinePlayer>> teamMap = new HashMap<>();

    public void addPlayerToTeam(String teamName, OfflinePlayer player) {
        if (teamMap.containsKey(teamName)) {
            teamMap.get(teamName).add(player);
        } else {
            teamMap.put(teamName, List.of(player));
        }
    }

    public void removePlayerFromTeam(String teamName, OfflinePlayer player) {
        if (teamMap.containsKey(teamName)) {
            teamMap.get(teamName).remove(player);
        }
    }

    public List<OfflinePlayer> getPlayersInTeam(String teamName) {
        return teamMap.get(teamName);
    }

    public boolean isPlayerInTeam(String teamName, OfflinePlayer player) {
        return teamMap.get(teamName).contains(player);
    }

    public boolean isTeamExist(String teamName) {
        return teamMap.containsKey(teamName);
    }

    public void addTeam(String teamName) {
        teamMap.put(teamName, new ArrayList<>());
    }

    public void removeTeam(String teamName) {
        teamMap.remove(teamName);
    }

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

    public void save(FileConfiguration config) {
        for (String teamName : teamMap.keySet()) {
            config.set("team." + teamName, teamMap.get(teamName).stream().map(OfflinePlayer::getUniqueId).map(UUID::toString).toArray());
        }
    }

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
