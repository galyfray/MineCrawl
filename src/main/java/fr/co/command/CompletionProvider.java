package fr.co.command;

import java.util.List;

public interface CompletionProvider {
    /**
     * Returns the list of completion proposals for the given prefix.
     * It is safe to not filter the possible proposals however it is recommended to do so for ease of use.
     *
     * @param prefix the prefix to complete
     * @return the list of completion proposals. It is unrecommended but safe to return null.
     */
    List<String> getPossibilities(String prefix);
}
