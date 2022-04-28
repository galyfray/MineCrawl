package fr.co;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Localizer {
    private final static String BUNDLE = "localization.dictionary";
    private static final Localizer instance = new Localizer();
    private final Locale locale;

    private Localizer() {
        locale = Locale.getDefault();
    }

    /**
     * Get the value of a key in the dictionary.
     * @param key the key to get the value of
     * @return the value of the key
     */
    public String getLocalizedText(String key) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE, locale, this.getClass().getClassLoader());

            if (bundle.keySet().contains(key)) {
                return bundle.getString(key);
            } else {
                return key;
            }
        } catch (Exception e) {
            MineCrawl.LOGGER.log(Level.WARNING, String.format("Error while loading localisation for key %s stacktrace : %s", key, Arrays.toString(e.getStackTrace())));
            return key;
        }
    }

    public static Localizer getInstance() {
        return instance;
    }
}
